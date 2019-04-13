package game.data.analyzer.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.data.analyzer.data.EffectRepository;
import game.data.analyzer.model.Effect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EffectService {

    @Autowired
    private EffectRepository effectRepository;

    public EffectService(EffectRepository effectRepository) {
        this.effectRepository = effectRepository;
    }

    public Effect get(long id) {
        Optional<Effect> effect = effectRepository.findById(id);
        if (!effect.isPresent()) throw new ItemDoesntExist();
        else return effect.get();
    }

    public List<Effect> findAll() {
        return effectRepository.findAll();
    }

    public void save(Effect effect) {
        effectRepository.save(effect);
    }

    public List<Effect> retrieveByName(String name) {
        if (name == null || name.equals("")) return findAll();
        return effectRepository.findByNameContainingIgnoreCase(name);
    }

}
