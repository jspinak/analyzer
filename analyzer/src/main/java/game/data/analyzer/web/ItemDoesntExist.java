package game.data.analyzer.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Item not found :/")
public class ItemDoesntExist extends RuntimeException {
}
