package team_project.beer_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.LikeBeerRepository;
import team_project.beer_community.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)  //readOnly를 전체 적용 시켜놓고 write 하는 경우에만 @Transactional을 붙여준다.
@RequiredArgsConstructor // @Autowired
public class UserService {
    private final UserRepository userRepository;
    private final LikeBeerRepository likeBeerRepository;
    private final LikeBeerService likeBeerService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long join(User user){
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    //findById return 값이 optional 이므로 orElseThrow()로 값이 없으면 에러를 발생시킨다.
    public User findOne(Long id){
        return userRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public List<Comment> findComments(Long id){
        User findUser = userRepository.findById(id).orElseThrow(NullPointerException::new);
        return findUser.getComments();
    }

    public List<LikeBeer> findLikeBeers(Long id){
        User findUser = userRepository.findById(id).orElseThrow(NullPointerException::new);
        return findUser.getLikeBeers();
    }

    @Transactional
    public void updateName(Long id, String name){
        User findUser = userRepository.findById(id).orElseThrow(NullPointerException::new);
        findUser.setUsername(name);
    }

    @Transactional
    public void updateEmail(Long id, String email){
        User findUser = userRepository.findById(id).orElseThrow(NullPointerException::new);
        findUser.setEmail(email);
    }

//    @Transactional
//    public void updatePassword(Long id, String rawPassword){
//        User findUser = userRepository.findById(id).orElseThrow(NullPointerException::new);
//        String password = bCryptPasswordEncoder.encode(rawPassword);
//        findUser.setPassword(password);
//    }

    @Transactional
    public void addLikeBeer(Long userid, LikeBeer likeBeer){
        User findUser = userRepository.findById(userid).orElseThrow(NullPointerException::new);
        findUser.addLikeBeer(likeBeer);  //User에 likeBeer 추가
        likeBeer.getBeer().addLikeBeer(likeBeer);  //Beer에 likeBeer 추가
    }

    @Transactional
    public void deleteLikeBeer(Long userid, Long beerId){
        User findUser = userRepository.findById(userid).orElseThrow(NullPointerException::new);
        List<LikeBeer> likeBeers = likeBeerService.findAllWithBeer(userid); //fetch join 사용
        for (LikeBeer likeBeer : likeBeers) {
            if (likeBeer.getBeer().getId() == beerId){
                findUser.getLikeBeers().remove(likeBeer); //User에서 likeBeer 삭제
                likeBeer.getBeer().getLikeBeers().remove(likeBeer); //Beer에서 likeBeer 삭제
                likeBeerRepository.delete(likeBeer);  //LikeBeer table에서 likeBeer 삭제
                break;
            }
        }
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validateResult = new HashMap<>();

        for (FieldError fieldError : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", fieldError.getField());
            validateResult.put(validKeyName, fieldError.getDefaultMessage());
        }
        return validateResult;
    }

    public User getUserWithInitializedLikeBeers(Long userId){
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        user.getLikeBeers().stream().forEach(lb -> lb.getId());
        return user;
    }
    public User getUserWithInitializedComments(Long userId){
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        user.getComments().stream().forEach(c -> c.getId());
        return user;
    }
}
