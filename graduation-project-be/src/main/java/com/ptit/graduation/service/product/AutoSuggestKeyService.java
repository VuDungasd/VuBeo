package com.ptit.graduation.service.product;

import java.util.List;
import java.util.Set;

public interface AutoSuggestKeyService {
  Set<String> getAutoSuggest(String prefix);
}
