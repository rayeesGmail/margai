import 'dart:collection';
import 'dart:convert';
import 'dart:typed_data';

import 'package:dio/dio.dart';

/// A canned reply from the fake server.
class FakeReply {
  const FakeReply(
    this.status,
    this.body, {
    this.contentType = Headers.jsonContentType,
    this.headers = const <String, String>{},
  });

  /// A TECH_PLAN §3.3 envelope.
  factory FakeReply.envelope(
    int status,
    String code, {
    String messageEn = 'message-en',
    String messageUser = 'message-user',
    Map<String, Object?> details = const <String, Object?>{},
    Map<String, String> headers = const <String, String>{},
  }) {
    final error = <String, Object?>{
      'code': code,
      'message_en': messageEn,
      'message_user_lang': messageUser,
      if (details.isNotEmpty) 'details': details,
    };
    return FakeReply(status, jsonEncode({'error': error}), headers: headers);
  }

  factory FakeReply.json(int status, Map<String, Object?> body) =>
      FakeReply(status, jsonEncode(body));

  final int status;
  final String body;
  final String contentType;
  final Map<String, String> headers;
}

/// One captured request: the options dio built plus the raw body it streamed.
class FakeRequest {
  const FakeRequest(this.options, this.body);

  final RequestOptions options;
  final String body;

  Map<String, Object?> get json =>
      jsonDecode(body) as Map<String, Object?>;

  String? header(String name) {
    for (final entry in options.headers.entries) {
      if (entry.key.toLowerCase() == name.toLowerCase()) {
        return entry.value?.toString();
      }
    }
    return null;
  }
}

/// A dio adapter that answers from a queue of replies (or throws the queued error) and records
/// every request, so client and repository tests need no network and no mocking library.
class FakeAdapter implements HttpClientAdapter {
  final Queue<Object> _script = Queue<Object>();
  final List<FakeRequest> requests = <FakeRequest>[];

  FakeRequest get last => requests.last;

  void reply(FakeReply reply) => _script.add(reply);

  /// The next fetch throws [error] (a [DioException] or any other object, as an adapter would).
  void fail(Object error) => _script.add(_Throw(error));

  @override
  Future<ResponseBody> fetch(
    RequestOptions options,
    Stream<Uint8List>? requestStream,
    Future<void>? cancelFuture,
  ) async {
    final body = requestStream == null
        ? ''
        : utf8.decode(
            await requestStream.fold<List<int>>(
              <int>[],
              (all, chunk) => all..addAll(chunk),
            ),
          );
    requests.add(FakeRequest(options, body));
    if (_script.isEmpty) {
      throw StateError('FakeAdapter: no reply scripted for ${options.path}');
    }
    final next = _script.removeFirst();
    if (next is _Throw) {
      throw next.error;
    }
    final reply = next as FakeReply;
    return ResponseBody.fromString(
      reply.body,
      reply.status,
      headers: <String, List<String>>{
        Headers.contentTypeHeader: <String>[reply.contentType],
        for (final entry in reply.headers.entries)
          entry.key.toLowerCase(): <String>[entry.value],
      },
    );
  }

  @override
  void close({bool force = false}) {}
}

class _Throw {
  const _Throw(this.error);

  final Object error;
}
