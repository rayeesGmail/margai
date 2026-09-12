package com.margai.curriculum.internal;

import com.margai.curriculum.api.ArchetypeTrackRow;
import com.margai.curriculum.api.BackboneLoadReport;
import com.margai.curriculum.api.BookLanguage;
import com.margai.curriculum.api.CurriculumImport;
import com.margai.curriculum.api.CutoffLoadReport;
import com.margai.curriculum.api.CutoffRow;
import com.margai.curriculum.api.NcertBookRow;
import com.margai.curriculum.api.NcertLoadReport;
import com.margai.curriculum.api.NcertParagraphRow;
import com.margai.curriculum.api.NcertRegisterReport;
import com.margai.curriculum.api.PrerequisiteLoadReport;
import com.margai.curriculum.api.PrerequisiteRow;
import com.margai.curriculum.api.SyllabusNodeRow;
import com.margai.curriculum.api.TaxonomyLoadReport;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link CurriculumImport} over the D4 tables (TECH_PLAN §2.3, §6.3): one importer per load, one
 * transaction per call — the importer checks the file against itself and against the database,
 * upserts by natural key, and any {@code CurriculumImportException} rolls the whole call back.
 * Nothing is deleted except a track's stale step sequences; rows the file no longer names are
 * reported as orphans (DECISIONS 2026-09-12 D13).
 */
@Service
@Transactional
class CurriculumImportService implements CurriculumImport {

    private final TaxonomyImporter taxonomy;
    private final PrerequisiteImporter prerequisites;
    private final BackboneImporter backbone;
    private final CutoffImporter cutoffs;
    private final NcertBookImporter ncertBooks;
    private final NcertParagraphImporter ncertParagraphs;

    CurriculumImportService(TaxonomyImporter taxonomy, PrerequisiteImporter prerequisites, BackboneImporter backbone,
            CutoffImporter cutoffs, NcertBookImporter ncertBooks, NcertParagraphImporter ncertParagraphs) {
        this.taxonomy = taxonomy;
        this.prerequisites = prerequisites;
        this.backbone = backbone;
        this.cutoffs = cutoffs;
        this.ncertBooks = ncertBooks;
        this.ncertParagraphs = ncertParagraphs;
    }

    @Override
    public TaxonomyLoadReport loadTaxonomy(List<SyllabusNodeRow> rows) {
        return taxonomy.load(rows);
    }

    @Override
    public PrerequisiteLoadReport loadPrerequisites(List<PrerequisiteRow> rows) {
        return prerequisites.load(rows);
    }

    @Override
    public BackboneLoadReport loadBackbone(List<ArchetypeTrackRow> tracks) {
        return backbone.load(tracks);
    }

    @Override
    public CutoffLoadReport loadCutoffs(List<CutoffRow> rows) {
        return cutoffs.load(rows);
    }

    @Override
    public NcertRegisterReport registerBooks(List<NcertBookRow> rows) {
        return ncertBooks.register(rows);
    }

    @Override
    public void recordRenderedPages(String bookCode, BookLanguage language, int pages) {
        ncertBooks.recordRenderedPages(bookCode, language, pages);
    }

    @Override
    public Integer renderedPages(String bookCode, BookLanguage language) {
        return ncertBooks.renderedPages(bookCode, language);
    }

    @Override
    public NcertLoadReport loadParagraphs(String bookCode, BookLanguage language, List<NcertParagraphRow> rows) {
        return ncertParagraphs.load(bookCode, language, rows);
    }
}
