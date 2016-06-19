package com.wacajou.data.jpa.domain;

public interface ParcoursInterface {
	
	public String getDescription();

	public void setDescription(String description);

	public User getRespo();

	public void setRespo(User user);

	public String getName();

	public void setName(String name);
	
	public Domain getDomain();
	
	public void setDomain(Domain domain);

	public String getLink();

	public void setLink(String link);

}
