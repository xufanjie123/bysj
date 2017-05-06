package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.dao.SectionDao;
import com.hospital.entity.Section;

@Service
public class SectionService {
	@Autowired
	private SectionDao sectionDao;
	public List<Section> getSections(){
		List<Section> sections = sectionDao.getSections();
		Section section = new Section();
		section.setId(0);
		section.setSectionname("请选择...");
		sections.add(0, section);
		return sections;
	}
}
