package com.ptit.graduation.controller.product;

import com.ptit.graduation.dto.request.product.ProductFilterRequest;
import com.ptit.graduation.dto.response.ResponseGeneral;
import com.ptit.graduation.dto.response.product.ProductListResponse;
import com.ptit.graduation.dto.response.product.ProductPageResponse;
import com.ptit.graduation.entity.product.ProductMongo;
import com.ptit.graduation.entity.user.UserMongo;
import com.ptit.graduation.facade.ProductFacadeService;
import com.ptit.graduation.repository.user.UserMongoRepository;
import com.ptit.graduation.service.product.ProductMongoService;
import com.ptit.graduation.service.product.ProductRedisService;
import com.ptit.graduation.service.product.RedisService;
import com.ptit.graduation.service.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ptit.graduation.constants.GraduationProjectConstants.MessageCode.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
  private final ProductMongoService productMongoService;
  private final ProductFacadeService productFacadeService;
  private final RedisService redisService;
  private final JwtUtils jwtUtils;
  private final UserMongoRepository userMongoRepository;

  private final ProductRedisService productRedisService;

  @PostMapping("/filter")
  public ResponseGeneral<ProductPageResponse> filter(
        @RequestBody ProductFilterRequest request
  ) {
    log.info("(filter) request: {}", request);

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          productFacadeService.filter(request)
    );
  }

  @GetMapping("/get/{id}")
  public ResponseGeneral<ProductMongo> get(@PathVariable String id) {
    log.info("(get) id: {}", id);

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          productMongoService.get(id)
    );
  }

  @GetMapping("")
  public ResponseGeneral<ProductListResponse> getAll(
        @RequestParam(defaultValue = "0") int skip,
        @RequestParam(defaultValue = "50") int limit
  ) {
    log.info("(getAll) skip: {}, limit: {}", skip, limit);

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          productMongoService.getAll(skip, limit)
    );
  }

  @GetMapping("/category/{categoryId}")
  public ResponseGeneral<ProductListResponse> getByCategoryId(
        @PathVariable String categoryId,
        @RequestParam(defaultValue = "0") int skip,
        @RequestParam(defaultValue = "50") int limit
  ) {
    log.info("(getByCategoryId) categoryId: {}, skip: {}, limit: {}", categoryId, skip, limit);

    return ResponseGeneral.ofSuccess(
          SUCCESS,
          productMongoService.getByCategoryId(categoryId, skip, limit)
    );
  }


  // API để người dùng xem chi tiết sản phẩm
  @PostMapping("/view")
  public void viewProduct(@RequestParam String token, @RequestParam String productId) {
    String userIdOrSession = getUserIdFromToken(token); // Hàm để lấy userId từ token
    redisService.addProductToViewed(userIdOrSession, productId);
  }

  // API để lấy danh sách sản phẩm đã xem
  @GetMapping("/viewed")
  public List<Object> getViewedProducts(@RequestParam String token) {
    String userIdOrSession = getUserIdFromToken(token); // Hàm để lấy userId từ token
    return redisService.getViewedProducts(userIdOrSession);
  }

  // Hàm giả lập lấy userId từ token (có thể thay bằng JWT token decode)
  private String getUserIdFromToken(String token) {
    // Logic để lấy userId từ token
    String username = jwtUtils.extractUsername(token);
    return userMongoRepository.findByUsername(username).getId(); // Ví dụ, dùng chính token làm userId (có thể thay đổi tuỳ theo token)
  }

}
