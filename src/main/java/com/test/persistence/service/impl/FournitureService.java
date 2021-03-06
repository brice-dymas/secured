/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.persistence.service.impl;

import com.test.persistence.dao.ICategorieDao;
import com.test.persistence.dao.IFournitureDao;
import com.test.persistence.model.Categorie;
import com.test.persistence.model.Fourniture;
import com.test.persistence.service.IFournitureService;
import com.test.persistence.service.common.AbstractService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author samuel   < smlfolong@gmail.com >
 */
@Service("fournitureService")
public class FournitureService extends AbstractService<Fourniture> implements IFournitureService
{

    @Autowired
    private IFournitureDao iFournitureDao;

    @Autowired
    private ICategorieDao iCategorieDao;

    @Override
    protected PagingAndSortingRepository<Fourniture, Long> getDao()
    {
        return iFournitureDao;
    }

    @Override
    public List<Fourniture> findExisting()
    {
        return iFournitureDao.findExisting();
    }

    @Override
    public Fourniture create(Fourniture entity)
    {
        entity.setCategorie(iCategorieDao.findOne(entity.getCategorie().getId()));
        return iFournitureDao.save(entity);
    }

    @Override
    public Fourniture update(Fourniture entity)
    {
        Fourniture fournitureToUpdate = iFournitureDao.findOne(entity.getId());
        fournitureToUpdate.setReference(entity.getReference());
        fournitureToUpdate.setDesignation(entity.getDesignation());
//        fournitureToUpdate.setQuantite(entity.getQuantite());
        fournitureToUpdate.setSeuil(entity.getSeuil());
        fournitureToUpdate.setCategorie(iCategorieDao.findOne(entity.getCategorie().getId()));
        return iFournitureDao.save(fournitureToUpdate);
    }

    @Override
    public void delete(Fourniture entity)
    {
        iFournitureDao.delete(entity);
    }

    @Override
    public void deleteById(long entityId)
    {
        iFournitureDao.delete(entityId);
    }

    @Override
    public List<Fourniture> findByCategorie(Categorie categorie)
    {
        return iFournitureDao.findByCategorie(categorie);
    }

    @Override
    public Map<Long, String> findByCategorieName(String categorie)
    {
        final List<Fourniture> fournitures = iFournitureDao.findByCategorieName('%' + categorie + '%');
        Map<Long, String> listMap = new HashMap<>();
        for (Fourniture fourniture : fournitures)
        {
            listMap.put(fourniture.getId(),
                    fourniture.getDesignation());
        }
        return listMap;
    }

    @Override
    public Page<Fourniture> findPaginated(Long Id, String designation, String reference, int nombrePage, Integer size)
    {
        System.out.println("lancement de la recherche ...");
        System.out.println("affichage des parametres ...");
        System.out.println("categorieID= " + Id + " designation = " + designation + " reference = " + reference);
        System.out.println("execution de la requete ...");
        if (Id == -1)
        {
            System.out.println("sans parametre de type categorie");
            return iFournitureDao.findPaginated('%' + designation + '%', '%' + reference + '%', new PageRequest(nombrePage, size));
        }
        else
        {
            System.out.println("avec parametre de type categorie");
            return iFournitureDao.findPaginated(Id, '%' + designation + '%', '%' + reference + '%', new PageRequest(nombrePage, size));
        }

    }
}
