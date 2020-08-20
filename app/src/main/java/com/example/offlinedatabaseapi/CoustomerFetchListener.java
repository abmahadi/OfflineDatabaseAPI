package com.example.offlinedatabaseapi;

import java.util.List;

public interface CoustomerFetchListener {
    void onDeliverAllCoustomer(List<Coustomer> coustomerList);

    void onDeliverCoustomer(Coustomer coustomer);

    void onHideDialog();
}
