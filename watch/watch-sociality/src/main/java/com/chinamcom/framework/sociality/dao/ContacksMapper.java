package com.chinamcom.framework.sociality.dao;

import java.util.List;

import com.chinamcom.framework.sociality.model.Contact;
import com.chinamcom.framework.sociality.model.ContactPhone;

public interface ContacksMapper {

	void insertContactInfo(Contact contact);

	void insertContactPhone(List<ContactPhone> phones);

	List<Contact> selectContact(Contact contact);

	List<ContactPhone> selectContactPhone(ContactPhone phones);

	void updateContactInfo(Contact contact);

	void updateWatchstatByContact(Contact contact);

	void updateContactPhone(ContactPhone phones);

	void updateBatchContactPhone(List<ContactPhone> phones);

	void deleteContactInfo(Contact contact);

	void deleteContactPhone(ContactPhone phones);

	void deleteBatchContactPhone(List<ContactPhone> phones);
}
