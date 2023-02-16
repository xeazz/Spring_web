package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    public final AtomicLong postID = new AtomicLong(1);
    private final ConcurrentMap<Long, Post> postsMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return postsMap.values().stream()
                .filter(post -> !post.getIsRemove())
                .collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        Optional<Post> optionalPost = postsMap.values().stream()
                .filter(x -> x.getId() == id && !x.getIsRemove())
                .findFirst();
        if (optionalPost.isPresent()) {
            return optionalPost;
        } else {
            throw new NotFoundException();
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            postsMap.put(postID.get(), new Post(postID.get(), post.getContent()));
            postID.incrementAndGet();
            return post;
        } else {
            if (post.getId() != 0 && postsMap.containsKey(post.getId()) && !postsMap.get(post.getId()).getIsRemove()) {
                postsMap.replace(post.getId(), post);
                return post;
            } else {
                throw new NotFoundException();
            }
        }
    }

    public void removeById(long id) {
        Optional<Map.Entry<Long, Post>> optionalEntry = postsMap.entrySet().stream()
                .filter(x -> x.getKey() == id && !x.getValue().getIsRemove())
                .findFirst();
        if (optionalEntry.isPresent()) {
            optionalEntry.get().getValue().setRemove(true);
        } else {
            throw new NotFoundException();
        }
    }
}
