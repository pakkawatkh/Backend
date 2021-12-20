package com.example.backend.api;

import com.example.backend.business.OrderBusiness;
import com.example.backend.entity.Orders;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.FileException;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderStatusReq;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.OrderService;
import com.example.backend.service.token.TokenService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderApi {

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    private final OrdersRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final TokenService tokenService;
    private final OrderBusiness business;

    public OrderApi(OrdersRepository orderRepository, UserRepository userRepository, OrderService orderService, TokenService tokenService, OrderBusiness business) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.tokenService = tokenService;
        this.business = business;
    }

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderReq req) throws BaseException {
        //GET USER FROM TOKEN (TYPE IS OBJECT)
//        User user = tokenService.getUserByToken();
//        Optional<Orders> order = orderService.createOrder(user, req.getStatus(), new Date());
        return "order";
    }

    @PostMapping("/getOrder")
    public Optional<Orders> getOrders(Integer id) {
        Optional<Orders> byId = orderRepository.findById(id);
        return byId;
    }

    @PostMapping("/cancel")
    public Object cancel(@RequestBody OrderStatusReq req) throws BaseException {

        Object res = business.changeStatus(req, Orders.Status.CANCEL);
        return res;
    }

    @PostMapping("/success")
    public Object success(@RequestBody OrderStatusReq req) throws BaseException {

        Object res = business.changeStatus(req, Orders.Status.SUCCESS);
        return res;
    }

    @PostMapping("/file")
    public MultipartFile uploadProfilePicture(MultipartFile file) throws FileException {

        //validate file
        if (file == null) {
            //throw error
            throw FileException.fileNull();
        }

        //validate size
        if (file.getSize() > 1048576 * 5) {
            //throw error
            throw FileException.fileMaxSiza();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            //throw  error
            throw FileException.unsupported();

        }

        List<String> supeportesTypes = Arrays.asList("image/jpeg", "image/png");
        if (!supeportesTypes.contains(contentType)) {
            //throw error (unsupport)
        }
        // TODO: upload file Storage (AWS S3, etc......)
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }

    public String UploadPage(Model model) {
        return "uploadview";
    }

    @RequestMapping("/upload")
    public String upload(MultipartFile file) {
        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename() + " ");
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        model.addAttribute("msg", "Successfully uploaded files " + fileNames.toString());
        return "uploadstatusview";
    }

}
