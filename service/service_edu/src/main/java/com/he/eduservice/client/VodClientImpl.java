package com.he.eduservice.client;

import com.he.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVideoByid(String videoId) {


        return R.error().message("熔断器熔断 ");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("熔断器熔断 ");
    }
}
