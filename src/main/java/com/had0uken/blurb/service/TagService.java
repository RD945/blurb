package com.had0uken.blurb.service;

import com.had0uken.blurb.payload.responses.Response;

public interface TagService {
    Response getStoriesByTag(String tag);
    Response getPostsByTag(String tag);
}
