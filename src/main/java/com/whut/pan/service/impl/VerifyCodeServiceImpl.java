package com.whut.pan.service.impl;

import com.whut.pan.dao.IVerifyCodeRepository;
import com.whut.pan.domain.VerifyCode;
import com.whut.pan.service.IVerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zc on 2018/12/4.
 */
@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService{
    @Autowired
    private IVerifyCodeRepository iVerifyCodeRepository;

    @Override
    public List<VerifyCode> findVerifyCodeByCustomName(String customName) {
        return iVerifyCodeRepository.findVerifyCodeByCustomName(customName);
    }

    @Override
    public int modifyVerifyState(VerifyCode verifyCode) {
        return iVerifyCodeRepository.setVerifyCode(true,verifyCode.getCustomName(),verifyCode.getOperatePerson(),verifyCode.getRegisterCode());
    }

    @Override
    public boolean save(VerifyCode verifyCode) {
        boolean result=false;
        VerifyCode verifyCodeList=iVerifyCodeRepository.findVerifyCodeByCustomNameAndOperatePersonAndRegisterCode(verifyCode.getCustomName(),verifyCode.getOperatePerson(),verifyCode.getRegisterCode());
        if(verifyCodeList==null){
            iVerifyCodeRepository.save(verifyCode);
            result=true;
        }
        return result;
    }

    @Override
    public VerifyCode findVerifyCodeByCOR(String customName, String operatePerson, String registerCode) {
        return iVerifyCodeRepository.findVerifyCodeByCustomNameAndOperatePersonAndRegisterCode(customName,operatePerson,registerCode);
    }

    @Override
    public boolean isValid(String registerCode) {
        boolean result=false;
        registerCode=registerCode.trim();
        List<VerifyCode> verifyCodeList=iVerifyCodeRepository.findVerifyCodeByRegisterCode(registerCode);
        for(int i=0;i<verifyCodeList.size();i++){
            VerifyCode verifyCode=verifyCodeList.get(i);
            if(!verifyCode.isState()){
                int temp=iVerifyCodeRepository.modifyStateById(true,verifyCode.getId());
                System.out.println(temp);
                if(temp!=-1){
                    result=true;
                }
                break;
            }
        }
        return result;
    }



}