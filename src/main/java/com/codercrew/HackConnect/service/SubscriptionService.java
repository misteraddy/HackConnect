package com.codercrew.HackConnect.service;

import com.codercrew.HackConnect.model.PlanType;
import com.codercrew.HackConnect.model.Subscription;
import com.codercrew.HackConnect.model.User;
import com.codercrew.HackConnect.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));  // Fix the method name
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptionRepository.save(subscription);
    }


    public Subscription getUsersSubscription(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception("Subscription not found for user id: " + userId));

        if (!isValid(subscription)) {
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12)); // Fixed method name and variable
            subscription.setSubscriptionStartDate(LocalDate.now());
            subscription = subscriptionRepository.save(subscription);
        }

        return subscription;
    }


    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Subscription not found for user id: " + userId));

        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if (planType.equals(PlanType.ANNUALLY)) {
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12)); // Fixed method name
        } else {
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1)); // Fixed method name
        }

        return subscriptionRepository.save(subscription);
    }


    public boolean isValid(Subscription subscription) {
        if (subscription.getPlanType().equals(PlanType.FREE)) {
            return true;
        }

        LocalDate endDate = subscription.getSubscriptionEndDate(); // Fixed variable name
        LocalDate currentDate = LocalDate.now();

        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate); // Fixed logical OR and variable name
    }

}
