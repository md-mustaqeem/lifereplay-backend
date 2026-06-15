package com.life.ai.service;

import com.life.ai.model.LifeEvent;
import com.life.ai.repository.LifeEventRepository;
import com.life.ai.repository.MemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BurnoutService {

    private  final MemoryRepository memoryRepository;

    public String moodStatus(Long userId){

        List<LifeEvent> list =
                memoryRepository.findTop5ByUserIdOrderByCreatedAtDesc(userId);

        if(list.size() < 3) return "STABLE";

        // Count moods
        long happy = list.stream().filter(e -> e.getMood().equals("Happy")).count();
        long good  = list.stream().filter(e -> e.getMood().equals("Good")).count();

        //  HAPPY
        if (happy >= 2) return "HAPPY";

        boolean threeNeg =
                isNegative(list.get(0)) &&
                        isNegative(list.get(1)) &&
                        isNegative(list.get(2));

        if(threeNeg) return "BURNOUT";

        boolean recovery =
                isNegative(list.get(0)) &&
                        isNegative(list.get(1)) &&
                        !isNegative(list.get(2));

        if(recovery) return "RECOVERY";

        return "STABLE";
    }


    //  REQUIRED FOR CONTROLLER
    public boolean isBurnout(Long userId){

        return "BURNOUT".equals(moodStatus(userId));
    }

    private boolean isNegative(LifeEvent e){

        return e.getMood().equals("Sad") || e.getMood().equals("Angry");
    }
}

