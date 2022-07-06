package ru.b19513.pet_schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.b19513.pet_schedule.controller.entity.FeedNoteDTO;
import ru.b19513.pet_schedule.controller.entity.PetDTO;
import ru.b19513.pet_schedule.controller.entity.StatusDTO;
import ru.b19513.pet_schedule.controller.entity.enums.Gender;
import ru.b19513.pet_schedule.controller.entity.enums.PetType;
import ru.b19513.pet_schedule.repository.entity.User;
import ru.b19513.pet_schedule.service.PetService;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/pets")
@Tag(name = "Pets controller", description = "Контроллер питомцев")
public class PetsController {

    private final PetService petService;

    @Autowired
    public PetsController(PetService petService) {
        this.petService = petService;
    }

    @Operation(summary = "Добавить нового питомца")
    @PostMapping("/createPet")
    public ResponseEntity<PetDTO> createPet(@RequestParam long groupId, @RequestParam String name,
                                            @RequestParam String description,
                                            @RequestParam Gender gender, @RequestParam PetType petType) {
        PetDTO petDTO = petService.createPet(groupId, name, description, gender, petType);
        return ResponseEntity.ok(petDTO);
    }

    @Operation(summary = "Обновить сведения о питомце")
    @PatchMapping("/")
    public ResponseEntity<PetDTO> updatePet(@RequestParam PetDTO petDTOInput) {
        PetDTO petDTO = petService.updatePet(petDTOInput);
        return ResponseEntity.ok(petDTO);
    }

    @Operation(summary = "Получить список питомцев по id группы")
    @GetMapping("/byGroup/{groupId}")
    public ResponseEntity<Collection<PetDTO>> getPets(@PathVariable long groupId) {
        Collection<PetDTO> PetDTOCollection = petService.getPets(groupId);
        return ResponseEntity.ok(PetDTOCollection);
    }

    @Operation(summary = "Удалить питомца и все связанные с ним напоминания и записи")
    @DeleteMapping("/{petId}")
    public ResponseEntity<StatusDTO> deletePet(@PathVariable long petId) {
        StatusDTO statusDTO = petService.deletePet(petId);
        return ResponseEntity.ok(statusDTO);
    }

    @Operation(summary = "Создать запись о кормлении")
    @PostMapping("/createFeedNote")
    public ResponseEntity<FeedNoteDTO> createFeedNote (Authentication auth, @RequestParam long petId,
                                                       @RequestParam String comment){
        var userId = ((User)auth.getDetails()).getId();
        FeedNoteDTO feedNoteDTO = petService.createFeedNote(petId, userId, comment);
        return ResponseEntity.ok(feedNoteDTO);
    }

    @Operation(summary = "Получить список записей о кормлении")
    @GetMapping("/{petId}/feedNotes")
    public ResponseEntity<Collection<FeedNoteDTO>> getFeedNotes(@PathVariable long petId) {
        Collection<FeedNoteDTO> feedNoteDTOCollection = petService.getFeedNotes(petId);
        return ResponseEntity.ok(feedNoteDTOCollection);
    }

    @Operation(summary = "Найти записи о кормежках по времени и дате")
    @GetMapping("/{petId}/feedNotesBetweenDates")
    public ResponseEntity<Collection<FeedNoteDTO>> findFeedNotesByDate(@PathVariable long petId,
                                                                       @RequestParam LocalDateTime from,
                                                                       @RequestParam LocalDateTime to) {
        Collection<FeedNoteDTO> feedNoteDTOCollection = petService.findFeedNotesByDate(petId, from, to);
        return ResponseEntity.ok(feedNoteDTOCollection);
    }
}
