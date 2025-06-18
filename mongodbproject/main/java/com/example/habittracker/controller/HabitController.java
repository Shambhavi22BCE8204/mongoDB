package com.example.habittracker.controller;

import com.example.habittracker.model.Habit;
import com.example.habittracker.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    @Autowired
    private HabitRepository habitRepository;

    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {
        return habitRepository.save(habit);
    }

    @GetMapping("/user/{userId}")
    public List<Habit> getHabitsByUser(@PathVariable String userId) {
        return habitRepository.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public Optional<Habit> getHabit(@PathVariable String id) {
        return habitRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Habit updateHabit(@PathVariable String id, @RequestBody Habit updatedHabit) {
        return habitRepository.findById(id).map(habit -> {
            habit.setTitle(updatedHabit.getTitle());
            habit.setDescription(updatedHabit.getDescription());
            habit.setCompleted(updatedHabit.isCompleted());
            habit.setFrequency(updatedHabit.getFrequency());
            return habitRepository.save(habit);
        }).orElseGet(() -> {
            updatedHabit.setId(id);
            return habitRepository.save(updatedHabit);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable String id) {
        habitRepository.deleteById(id);
    }
}
