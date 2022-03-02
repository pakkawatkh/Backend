package com.example.backend.mapper;

import com.example.backend.entity.TypeBuying;
import com.example.backend.entity.TypeBuyingList;
import com.example.backend.model.TypeBuyingModel.BuyingListResponse;
import com.example.backend.model.TypeBuyingModel.BuyingResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BuyingMapper {

    List<BuyingResponse> toBuyingResponse(List<TypeBuying> buying);

    List<BuyingListResponse> toBuyingListResponse(List<TypeBuyingList> buying);



}
