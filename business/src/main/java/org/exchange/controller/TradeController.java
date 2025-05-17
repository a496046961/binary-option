package org.exchange.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.exchange.controller.request.PlaceRequest;
import org.exchange.model.MessageResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trade")
public class TradeController {

    /**
     * 下单
     */
    @PostMapping("place")
    public MessageResult place(@RequestBody PlaceRequest placeRequest, HttpServletRequest request) {

        return MessageResult.success();
    }

}
