package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    public final AtomicLong postID = new AtomicLong(0);
    private final ConcurrentMap<Long, Post> postsMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(postsMap.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(postsMap.get(id));
    }

    public Post save(Post post) {
        if (post.getId() != 0 && postsMap.containsKey(post.getId())) {
            postsMap.replace(post.getId(), post);
        } else if (post.getId() == 0) {
            long newPostId = postID.incrementAndGet();
            post.setId(newPostId);
            postsMap.put(newPostId, post);
        }
        return post;
    }

    public void removeById(long id) {
        if (postsMap.containsKey(id)) {
            postsMap.remove(id);
        } else {
            System.out.println("Такого id не существует!");
        }
    }
}
