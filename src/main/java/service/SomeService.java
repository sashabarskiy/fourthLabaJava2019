package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeService {

    @Autowired
    DataBaseService dataBaseService;

    public void doSomething(){

    }

}

