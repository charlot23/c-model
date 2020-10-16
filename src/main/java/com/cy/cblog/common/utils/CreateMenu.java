package com.cy.cblog.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.cy.cblog.dto.Menu;
import com.cy.cblog.dto.Meta;
import com.cy.cblog.mbg.model.Permission;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreateMenu {

    public List<Menu> create(List<Permission> permission){

        List<Menu> Allmenu = new ArrayList<>();

        for (Permission p:permission){
            ArrayList<Menu> menus = new ArrayList<>();
            if (p.getParentid() == null){
                for (Permission c:permission){
                    if (p.getPid().equals(c.getParentid())){
                        Menu menu = new Menu();
                        Meta meta = new Meta();
                        BeanUtil.copyProperties(c,menu);
                        meta.setIcon(c.getIcon());
                        meta.setTitle(c.getTitle());
                        menu.setMeta(meta);
                        menus.add(menu);
                    }
                }
                Menu menu1 = new Menu();
                BeanUtil.copyProperties(p,menu1);
                menu1.setChildren(menus);
                Allmenu.add(menu1);
            }

        }


        return Allmenu;
    }
}
