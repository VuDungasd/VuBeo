package com.ptit.graduation.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGenerateProduct {
    
    private String[] WORDS = {
        // Đồ gia dụng
        "bàn", "ghế", "giường", "tủ", "đèn", "chăn", "gối", "nệm", "thảm", "rèm cửa", 
        "tủ lạnh", "máy giặt", "lò vi sóng", "bếp ga", "bếp điện", "nồi cơm điện", "ấm đun", "quạt điện", 
        "máy hút bụi", "máy sấy tóc", "máy xay sinh tố", "bàn là", "nồi chiên không dầu", "máy pha cà phê", 
    
        // Thiết bị điện tử
        "máy tính", "điện thoại", "tivi", "máy ảnh", "máy quay phim", "máy in", "máy fax", "loa", "micro",
        "bộ điều khiển", "màn hình", "máy chơi game", "máy chiếu", "dây sạc", "sạc dự phòng", "bộ định tuyến wifi",
        "máy lọc nước", "máy sấy quần áo", "máy lọc bụi", "laptop", "máy tính bảng", "tai nghe",
        "điện thoại di động", "máy nghe nhạc", "máy nghe nhạc bluetooth", "máy nghe nhạc không dây",
        
        // Đồ dùng nhà bếp
        "bộ dao", "nồi", "chảo", "muỗng", "dao", "thớt", "chén", "ly", "đĩa", "ấm trà", "bình đựng nước", 
        "bộ đồ ăn", "dĩa gỗ", "bộ ấm chén", "dụng cụ mở nắp chai", "kéo", "máy ép hoa quả", "máy xay thịt",
        
        // Vật dụng văn phòng
        "bàn làm việc", "ghế văn phòng", "kệ sách", "tủ hồ sơ", "bút bi", "bút dạ quang", "thước kẻ", "giấy ghi chú", 
        "sổ tay", "dụng cụ dập ghim", "giá đỡ tài liệu", "móc giấy", "băng dính", "kéo", "tẩy bút chì", "máy photocopy",
        
        // Công cụ và dụng cụ
        "cưa tay", "búa", "đinh", "ốc vít", "dụng cụ đo nhiệt độ", "dụng cụ ép tỏi", "máy khoan", "kìm", "mỏ lết", 
        "cờ lê", "thước đo", "cây lau nhà", "xẻng", "cuốc", "chổi", "kéo cắt cành", "dao rọc giấy",
        
        // Đồ nội thất
        "sofa", "bàn trà", "kệ tivi", "bàn trang điểm", "gương soi", "bàn học", "tủ đựng giày", "tủ quần áo", "giá sách",
        "kệ giày", "kệ trưng bày", "bàn ăn", "ghế gấp", "tủ bếp", "tủ kính", "ghế lười", "tủ âm tường", 
        
        // Đồ vệ sinh cá nhân
        "bàn chải đánh răng", "kem đánh răng", "dao cạo râu", "dầu gội", "sữa tắm", "khăn mặt", "bông tắm", 
        "bình xịt khử mùi", "lược", "gương nhỏ", "dao cạo", "móc treo quần áo", "máy sấy tóc",
        
        // Đồ dùng cá nhân
        "áo khoác", "quần jean", "giày thể thao", "kính mát", "mũ bảo hiểm", "móc khóa", "túi xách", "dây chuyền", 
        "nhẫn", "bông tai", "khuyên tai", "vòng tay", "dây đeo tay", "đồng hồ đeo tay", "khăn quàng cổ",
        
        // Đồ dùng trẻ em
        "xe đạp trẻ em", "ghế ăn cho bé", "bình sữa", "máy tiệt trùng bình sữa", "xe đẩy", "ghế ngồi ô tô cho bé", 
        "gối ngủ cho bé", "chăn cho bé", "cũi", "bỉm", "tã", "ghế gập cho bé", "giường trẻ em",
        
        // Đồ thể thao và du lịch
        "vợt cầu lông", "quả bóng rổ", "bóng chuyền", "bộ đồ leo núi", "kính bơi", "áo phao", "găng tay thể thao", 
        "mũ bảo hiểm xe đạp", "túi đựng giày", "balo leo núi", "bình nước thể thao", "giày thể thao", "áo thể thao",
        
        // Phụ kiện xe
        "bộ dụng cụ sửa xe", "gương chiếu hậu", "kính chắn gió", "bọc vô lăng", "đèn xi nhan", "đèn pha", 
        "thảm lót chân xe", "áo khoác xe", "mũ bảo hiểm", "bình xịt khử mùi xe", "gương phụ", "camera hành trình",
        
        // Vật dụng khác
        "thùng rác", "bình chữa cháy", "chậu hoa", "bình hoa", "bình phun sương", "tượng trang trí", "đồng hồ treo tường",
        "hộp cơm giữ nhiệt", "đồng hồ báo thức", "máy xông tinh dầu", "máy lọc không khí", "bàn để laptop",
        
        // Đồ dùng khác (tiếp tục bổ sung các vật dụng ngẫu nhiên)
        "bình nước giữ nhiệt", "kéo cắt tóc", "máy đo huyết áp", "máy đo nồng độ oxy", "kính lúp", "bàn cắt giấy",
        "máy rửa bát", "máy làm sữa hạt", "máy đánh trứng", "máy nhồi bột", "bộ sạc dự phòng", "nồi inox",
        "máy đo đường huyết", "cân sức khỏe", "áo chống nắng", "quạt sưởi", "bàn chải cọ sàn", "cây lau nhà tự động",
        "bình xịt vệ sinh", "giá treo đồ", "bộ đựng đồ nhà bếp", "đồng hồ đo điện", "máy cắt tỉa", "bình đựng dầu ăn",
        "máy tập cơ bụng", "dụng cụ kéo giãn cơ", "băng tay thể thao", "dây nhảy thể thao", "kính bảo hộ",
        
        // Đồ trang trí và lưu niệm
        "khung ảnh", "tranh treo tường", "vỏ gối", "đèn trang trí", "đèn ngủ led", "hộp quà", "thiệp mừng",
        "túi giấy đựng quà", "túi vải", "bình cắm hoa", "hộp đựng đồ", "gối ôm", "hộp đựng trang sức",
        "đĩa trang trí", "bình đựng nến", "hộp nhạc", "tượng Phật", "bình đựng tinh dầu", "đèn dầu trang trí"
    };    

    private String[] COLORS = {
        "màu xanh", "màu đỏ", "màu tím", "màu vàng", "màu đen", "màu trắng", "màu cam", "màu nâu", "màu hồng", "màu xám"
    };

    private String[] SIZE = {
        "cỡ nhỏ", "cỡ trung bình", "cỡ lớn", "cỡ rất lớn"
    };

    private String[] TYPES = {
        "Thời Trang Nam", 
        "Thời Trang Nữ", 
        "Thiết Bị Điện Tử", 
        "Thiết Bị Điện Gia Dụng", 
        "Thể Thao & Du Lịch", 
        "Ô Tô - Xe Máy - Xe Đạp", 
        "Giày Dép", "Phụ Kiện & Trang Sức", 
        "Nhà Cửa & Đời Sống",
        "Mẹ - Bé", 
        "Sức Khỏe - Làm Đẹp", 
        "Thực Phẩm", 
        "Sách"
    };

    private Map<String, List<String>> categoriesMap = new HashMap<>();

    private Map<String, List<String>> brandsMap = new HashMap<>();

    public DataGenerateProduct() {
        this.initCategoriesMap();
        this.initBrandsMap();
    }

    private void initCategoriesMap() {
        this.categoriesMap.put("Thời Trang Nam", Arrays.asList("Áo sơ mi", "Áo thun", "Quần jeans", "Quần kaki", "Áo khoác"));
        this.categoriesMap.put("Thời Trang Nữ", Arrays.asList("Đầm", "Chân váy", "Áo kiểu", "Quần legging", "Áo khoác"));
        this.categoriesMap.put("Thiết Bị Điện Tử", Arrays.asList("Điện thoại", "Laptop", "Máy tính bảng", "Tai nghe", "Máy ảnh"));
        this.categoriesMap.put("Thiết Bị Điện Gia Dụng", Arrays.asList("Máy giặt", "Tủ lạnh", "Điều hòa", "Nồi cơm điện", "Máy xay sinh tố"));
        this.categoriesMap.put("Thể Thao & Du Lịch", Arrays.asList("Giày chạy bộ", "Balo", "Áo thể thao", "Dụng cụ tập gym", "Đồng hồ thể thao"));
        this.categoriesMap.put("Ô Tô - Xe Máy - Xe Đạp", Arrays.asList("Phụ tùng xe", "Mũ bảo hiểm", "Dầu nhớt", "Bọc ghế", "Khóa chống trộm"));
        this.categoriesMap.put("Giày Dép", Arrays.asList("Giày thể thao", "Giày cao gót", "Sandal", "Dép xỏ ngón", "Giày lười"));
        this.categoriesMap.put("Phụ Kiện & Trang Sức", Arrays.asList("Dây chuyền", "Nhẫn", "Bông tai", "Vòng tay", "Kính mát"));
        this.categoriesMap.put("Nhà Cửa & Đời Sống", Arrays.asList("Đồ bếp", "Đồ nội thất", "Chăn ga", "Dụng cụ làm vườn", "Vệ sinh nhà cửa"));
        this.categoriesMap.put("Mẹ - Bé", Arrays.asList("Tã bỉm", "Đồ chơi", "Thực phẩm bổ sung", "Quần áo trẻ em", "Dụng cụ chăm sóc bé"));
        this.categoriesMap.put("Sức Khỏe - Làm Đẹp", Arrays.asList("Mỹ phẩm", "Thực phẩm chức năng", "Dụng cụ làm đẹp", "Nước hoa", "Vitamin"));
        this.categoriesMap.put("Thực Phẩm", Arrays.asList("Thực phẩm tươi sống", "Thực phẩm đông lạnh", "Gia vị", "Đồ uống", "Thực phẩm ăn liền"));
        this.categoriesMap.put("Sách", Arrays.asList("Sách văn học", "Sách giáo dục", "Sách thiếu nhi", "Truyện tranh", "Sách kỹ năng"));

    }

    private void initBrandsMap() {
        this.brandsMap.put("Thời Trang Nam", Arrays.asList("Việt Tiến", "An Phước", "Hugo Boss", "Levi's", "Lacoste"));
        this.brandsMap.put("Thời Trang Nữ", Arrays.asList("Elise", "Zara", "H&M", "Uniqlo", "Mango"));
        this.brandsMap.put("Thiết Bị Điện Tử", Arrays.asList("Apple", "Samsung", "Sony", "LG", "Dell"));
        this.brandsMap.put("Thiết Bị Điện Gia Dụng", Arrays.asList("Panasonic", "Electrolux", "Sharp", "Philips", "Toshiba"));
        this.brandsMap.put("Thể Thao & Du Lịch", Arrays.asList("Nike", "Adidas", "Puma", "The North Face", "Columbia"));
        this.brandsMap.put("Ô Tô - Xe Máy - Xe Đạp", Arrays.asList("Honda", "Yamaha", "BMW", "Ford", "Michelin"));
        this.brandsMap.put("Giày Dép", Arrays.asList("Converse", "Vans", "Aldo", "Gucci", "Dr. Martens"));
        this.brandsMap.put("Phụ Kiện & Trang Sức", Arrays.asList("Pandora", "Cartier", "Tissot", "Rolex", "Gucci"));
        this.brandsMap.put("Nhà Cửa & Đời Sống", Arrays.asList("Lock&Lock", "Tupperware", "Sunhouse", "IKEA", "Greenhome"));
        this.brandsMap.put("Mẹ - Bé", Arrays.asList("Pampers", "Huggies", "Chicco", "Fisher Price", "Nestlé"));
        this.brandsMap.put("Sức Khỏe - Làm Đẹp", Arrays.asList("L'Oréal", "Estée Lauder", "Clinique", "Shiseido", "DHC"));
        this.brandsMap.put("Thực Phẩm", Arrays.asList("Vissan", "CJ Foods", "Maggi", "Vinamilk", "Pepsi"));
        this.brandsMap.put("Sách", Arrays.asList("NXB Trẻ", "Kim Đồng", "Alphabooks", "NXB Giáo Dục", "NXB Văn Học"));
    }

    public String[] getWords() {
        return WORDS;
    }

    public String[] getTypes() {
        return TYPES;
    }

    public Map<String, List<String>> getCategoriesMap() {
        return categoriesMap;
    }

    public Map<String, List<String>> getBrandsMap() {
        return brandsMap;
    }

    public String[] getColors() {
        return COLORS;
    }

    public String[] getSizes() {
        return SIZE;
    }
 


}
