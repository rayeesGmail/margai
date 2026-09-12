package com.margai.pipeline.internal;

import static org.assertj.core.api.Assertions.assertThat;

import com.margai.storage.api.ObjectStore;
import com.margai.storage.api.StorageException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

/**
 * {@code ncert render} over a two-chapter book whose PDFs are generated here and kept in a
 * stand-in store: the page keys carry the printed chapter number, the images are real PNGs, a
 * re-run renders nothing, and the page count reaches {@code ncert_books}. The bucket itself is
 * exercised by the D14 acceptance run, never by a test.
 */
class NcertRenderCommandTest {

    @TempDir
    Path inputs;

    @TempDir
    Path reports;

    private final StringWriter out = new StringWriter();
    private final RecordingStore store = new RecordingStore();
    private final PipelineCommandTest.RecordingImport imports = new PipelineCommandTest.RecordingImport();
    private CommandLine commandLine;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(inputs.resolve(NcertRegisterCommand.FILE), """
                books:
                  - code: phy11-part2
                    subject: physics
                    class_level: 11
                    part: 2
                    title_en: "Physics Part-II, Textbook for Class XI"
                    edition_year: 2023
                    source:
                      en: source/ncert/2022-ed/en/phy11-part2/
                    chapters:
                      - {no: 8, en: keph201.pdf}
                      - {no: 9, en: keph202.pdf}
                """);
        store.put("source/ncert/2022-ed/en/phy11-part2/keph201.pdf", pdf(3), "application/pdf");
        store.put("source/ncert/2022-ed/en/phy11-part2/keph202.pdf", pdf(2), "application/pdf");
        store.puts.clear();

        Reports writer = new Reports(ReportTest.CLOCK);
        PipelineProperties properties = new PipelineProperties(72, 10, 1);
        CommandLine.IFactory siblings = PipelineCommandTest.siblingFactory(imports, writer);
        CommandLine.IFactory factory = new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> cls) throws Exception {
                if (cls == NcertRenderCommand.class) {
                    return cls.cast(new NcertRenderCommand(store, imports, properties, writer));
                }
                return siblings.create(cls);
            }
        };
        PrintWriter writerOut = new PrintWriter(out, true);
        commandLine = PipelineRunner.commandLine(factory).setOut(writerOut).setErr(writerOut);
    }

    @Test
    void rendersEveryPageUnderItsPrintedChapterNumber() {
        assertThat(render()).isZero();

        assertThat(store.list("pages/")).containsExactly(
                "pages/phy11-part2/en/8/001.png",
                "pages/phy11-part2/en/8/002.png",
                "pages/phy11-part2/en/8/003.png",
                "pages/phy11-part2/en/9/001.png",
                "pages/phy11-part2/en/9/002.png");
        assertThat(imports.renderedPages).containsExactly("phy11-part2 en 5");
    }

    @Test
    void everyRenderedPageIsARealImage() throws IOException {
        render();

        for (String key : store.list("pages/")) {
            assertThat(ImageIO.read(new ByteArrayInputStream(store.get(key)))).isNotNull();
        }
    }

    @Test
    void aSecondRunRendersNothing() {
        render();
        store.puts.clear();

        assertThat(render()).isZero();

        assertThat(store.puts).isEmpty();
        assertThat(out.toString())
                .contains("| chapter | source | pages | rendered | already there |")
                .contains("| 8 | keph201.pdf | 3 | 0 | 3 |")
                .contains("| 2 | 5 | 0 | 5 |");
    }

    /**
     * The skip is a liability when what is already there is wrong — as it was on the first real run,
     * whose pages were drawn without their JPEG2000 figures (D14).
     */
    @Test
    void redoRendersPagesAlreadyInTheBucket() {
        render();
        store.puts.clear();

        assertThat(commandLine.execute("ncert", "render", "--book", "phy11-part2", "--redo",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(store.puts).containsExactly(
                "pages/phy11-part2/en/8/001.png",
                "pages/phy11-part2/en/8/002.png",
                "pages/phy11-part2/en/8/003.png",
                "pages/phy11-part2/en/9/001.png",
                "pages/phy11-part2/en/9/002.png");
    }

    @Test
    void theReportNamesTheStoreItWroteTo() {
        render();

        assertThat(out.toString()).contains("content store: in-memory")
                .contains("| 8 | keph201.pdf | 3 | 3 | 0 |")
                .contains("| 9 | keph202.pdf | 2 | 2 | 0 |")
                .contains("ncert_books.pages_en = 5");
    }

    @Test
    void aChapterSubsetLeavesThePageCountAlone() {
        assertThat(commandLine.execute("ncert", "render", "--book", "phy11-part2", "--chapters", "9",
                "--inputs", inputs.toString(), "--reports", reports.toString())).isZero();

        assertThat(store.list("pages/")).containsExactly(
                "pages/phy11-part2/en/9/001.png", "pages/phy11-part2/en/9/002.png");
        assertThat(imports.renderedPages).isEmpty();
        assertThat(out.toString()).contains("left alone: this run rendered a chapter subset");
    }

    @Test
    void anEditionTheBookDoesNotHaveFailsTheRun() {
        assertThat(commandLine.execute("ncert", "render", "--book", "phy11-part2", "--lang", "hi",
                "--inputs", inputs.toString(), "--reports", reports.toString()))
                .isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("has no hi edition");
        assertThat(store.list("pages/")).isEmpty();
    }

    @Test
    void aBookTheFileDoesNotCarryFailsTheRun() {
        assertThat(commandLine.execute("ncert", "render", "--book", "bio11",
                "--inputs", inputs.toString(), "--reports", reports.toString()))
                .isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("does not carry a book with code 'bio11'");
    }

    @Test
    void aChapterTheBookDoesNotHaveFailsTheRun() {
        assertThat(commandLine.execute("ncert", "render", "--book", "phy11-part2", "--chapters", "8,12",
                "--inputs", inputs.toString(), "--reports", reports.toString()))
                .isEqualTo(InputFileCommand.EXIT_FAILED);

        assertThat(out.toString()).contains("does not have every chapter");
        assertThat(store.list("pages/")).isEmpty();
    }

    private int render() {
        return commandLine.execute("ncert", "render", "--book", "phy11-part2",
                "--inputs", inputs.toString(), "--reports", reports.toString());
    }

    /** A real PDF, so PDFBox does the work it will do on an NCERT file. */
    static byte[] pdf(int pages) {
        try (PDDocument document = new PDDocument()) {
            for (int page = 1; page <= pages; page++) {
                PDPage pdPage = new PDPage();
                document.addPage(pdPage);
                try (PDPageContentStream content = new PDPageContentStream(document, pdPage)) {
                    content.beginText();
                    content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 24);
                    content.newLineAtOffset(72, 700);
                    content.showText("page " + page);
                    content.endText();
                }
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            document.save(bytes);
            return bytes.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** The port, in a map, remembering every write so a re-run can be proved silent. */
    static final class RecordingStore implements ObjectStore {

        final Map<String, byte[]> objects = new LinkedHashMap<>();
        final List<String> puts = new ArrayList<>();

        @Override
        public void put(String key, byte[] bytes, String contentType) {
            puts.add(key);
            objects.put(key, bytes.clone());
        }

        @Override
        public byte[] get(String key) {
            byte[] bytes = objects.get(key);
            if (bytes == null) {
                throw new StorageException("no object at " + key);
            }
            return bytes.clone();
        }

        @Override
        public boolean exists(String key) {
            return objects.containsKey(key);
        }

        @Override
        public List<String> list(String prefix) {
            return objects.keySet().stream().filter(key -> key.startsWith(prefix)).sorted().toList();
        }

        @Override
        public String describe() {
            return "in-memory object store (nothing is persisted)";
        }
    }
}
