package telran.java57.forum.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telran.java57.forum.posts.dao.PostRepository;
import telran.java57.forum.posts.dto.exceptions.PostNotFoundException;
import telran.java57.forum.posts.model.Post;

@Service
public class WebExpressionMethod {

    @Autowired
    final PostRepository postRepository;

    public WebExpressionMethod(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean isOwnerOfPost(String userName, String postId){
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return post.getAuthor().equalsIgnoreCase(userName);
    }
}
