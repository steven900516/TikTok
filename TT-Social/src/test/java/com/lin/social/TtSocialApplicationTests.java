package com.lin.social;

import com.lin.social.dao.UserNodeRepository;
import com.lin.social.entity.node.UserNode;
import com.lin.social.entity.node.Video;
import com.lin.social.entity.relation.Follow;
import com.lin.social.entity.relation.Publish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class TtSocialApplicationTests {

    @Autowired
    UserNodeRepository userNodeRepository;

    @Test
    void contextLoads() {
        userNodeRepository.deleteAll();

        UserNode userNode2 = new UserNode("424233","321","林的2号","测试号");
        UserNode favorite1 = new UserNode("123","232324","林关注的人1","爸爸1");
        UserNode favorite2 = new UserNode("6464","232324vds","林关注的人2","爸爸2");
        UserNode favorite3 = new UserNode("321443","232323g4","林关注的人3","爸爸3");
        UserNode favorite4 = new UserNode("9493","23fwf","林关注的人4","爸爸4");
        userNodeRepository.save(userNode2);
        userNodeRepository.save(favorite1);
        userNodeRepository.save(favorite2);
        userNodeRepository.save(favorite3);
        userNodeRepository.save(favorite4);

        userNode2.getFollowList().add(new Follow(favorite1));
        userNode2.getFollowList().add(new Follow(favorite2));
        userNodeRepository.save(userNode2);

        favorite2.getFollowList().add(new Follow(favorite3));
        userNodeRepository.save(favorite2);


        favorite4.getFollowList().add(new Follow(favorite2));
        userNodeRepository.save(favorite4);


        byte[] img = new byte[]{};
        Video video = new Video("21312", "424233", img, "第一个视频", "#旅游", false, "北京", 0, 0, 0, 0);
        favorite1.getVideoList().add(new Publish(video));
        userNodeRepository.save(favorite1);


        System.out.println("user: " + userNodeRepository.findUserByUid("6464"));
        System.out.println("-------------------");
        System.out.println("fans[1,1]: " +  userNodeRepository.listUserFans("6464",1,1));
        System.out.println("fans[2,1]: " +  userNodeRepository.listUserFans("6464",2,1));

        System.out.println("fans[1,2]: " +  userNodeRepository.listUserFans("6464",1,2));


        System.out.println("----------------");
        System.out.println("follower " + userNodeRepository.listUserSubscribe("6464",1,1));


        System.out.println(userNodeRepository.count());
    }

}
