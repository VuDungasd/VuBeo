package com.ptit.graduation.utils;

public class ConvertVietnameseToNormalText {
    public String slugify(String input) {
        // Slugify logic here
        input =  this.toNonAccentVietnamese(input.trim());
        return input.toLowerCase().replaceAll("[^a-z0-9]+", "-");
    }
    public String toNonAccentVietnamese(String str) {
        str = str.replaceAll("[AÁÀÃẠÂẤẦẪẬĂẮẰẴẶ]", "A");
        str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        str = str.replaceAll("[EÉÈẼẸÊẾỀỄỆ]", "E");
        str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        str = str.replaceAll("[IÍÌĨỊ]", "I");
        str = str.replaceAll("[ìíịỉĩ]", "i");
        str = str.replaceAll("[OÓÒÕỌÔỐỒỖỘƠỚỜỠỢ]", "O");
        str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        str = str.replaceAll("[UÚÙŨỤƯỨỪỮỰ]", "U");
        str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
        str = str.replaceAll("[YÝỲỸỴ]", "Y");
        str = str.replaceAll("[ỳýỵỷỹ]", "y");
        str = str.replaceAll("[Đ]", "D");
        str = str.replaceAll("[đ]", "d");
        // Some system encode vietnamese combining accent as individual utf-8 characters
        str = str.replaceAll("[\\u0300\\u0301\\u0303\\u0309\\u0323]", ""); // Huyền sắc hỏi ngã nặng 
        str = str.replaceAll("[\\u02C6\\u0306\\u031B]", ""); // Â, Ê, Ă, Ơ, Ư
        return str;
    }
}
