/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.persistence.service;

import com.test.persistence.IOperations;
import com.test.persistence.model.Agence;
import org.springframework.data.domain.Page;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
public interface IAgenceService extends IOperations<Agence>
{

    Page<Agence> findPaginated(String code, String intitule, String region, int nombrePage, Integer size);
}
