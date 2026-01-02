package com.had0uken.blurb.service.implementation;

import com.had0uken.blurb.payload.responses.ContentResponse;
import com.had0uken.blurb.payload.responses.Response;
import com.had0uken.blurb.repository.PostRepository;
import com.had0uken.blurb.repository.StoriesRepository;
import com.had0uken.blurb.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final PostRepository postRepository;
    private final StoriesRepository storiesRepository;

    @Override
    public Response getPostsByTag(String tag) {
        return new ContentResponse<>(postRepository.findByTagName(tag), HttpStatus.OK);
    }

    @Override
    public Response getStoriesByTag(String tag) {
        return new ContentResponse<>(storiesRepository.findByTagName(tag), HttpStatus.OK);
    }


}
