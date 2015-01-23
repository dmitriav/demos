package com.vml.etl.dao.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vml.etl.dto.Article;

@Entity
@Table(name = "DATA")
public class ArticleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", columnDefinition = "int")
	private Integer id;
	@Column(name = "ARTICLEID", columnDefinition = "int")
	private Integer articleId;
	@Column(name = "ATTRIBUTE", columnDefinition = "varchar(20)")
	private String attribute;
	@Column(name = "VALUE", columnDefinition = "text")
	private String value;
	@Column(name = "LANGUAGE", columnDefinition = "smallint")
	private Integer language;
	@Column(name = "TYPE", columnDefinition = "smallint")
	private Integer type;


	/**
	 * Updates this entity with values from the specified article
	 */
	public void update(Article article) {
		if (article == null) {
			return;
		}

		this.id = article.getId();
		this.articleId = article.getArticleId();
		this.attribute = article.getAttribute();
		this.value = article.getValue();
		this.language = article.getLanguage();
		this.type = article.getType();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
