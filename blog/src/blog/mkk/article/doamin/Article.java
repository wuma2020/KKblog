package blog.mkk.article.doamin;

import java.util.List;

import blog.mkk.comment.domain.Comment;

public class Article {
	private int art_id;//文章id，主键自增长
	private String art_title;//文章标题
	private int art_type;//文章类型  0：技术文章  1.日记  2.杂记
	private String art_author;//文章作者
	private String art_date;//文章写作日期
	private String art_biaoqian;//文章标签
	private String art_content;//文章内容
	private String art_mark;//这个是用edtormd写的原内容，用于修改时读取
	private String art_image_url;//用于存储文章图片的url
	private List<Comment> comment;//评论对象,不止一个评论，用list
	public int getArt_id() {
		return art_id;
	}
	public void setArt_id(int art_id) {
		this.art_id = art_id;
	}
	public int getArt_type() {
		return art_type;
	}
	public void setArt_type(int art_type) {
		this.art_type = art_type;
	}
	public String getArt_author() {
		return art_author;
	}
	public void setArt_author(String art_author) {
		this.art_author = art_author;
	}
	public String getArt_biaoqian() {
		return art_biaoqian;
	}
	public void setArt_biaoqian(String art_biaoqian) {
		this.art_biaoqian = art_biaoqian;
	}
	public String getArt_content() {
		return art_content;
	}
	public void setArt_content(String art_content) {
		this.art_content = art_content;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public String getArt_title() {
		return art_title;
	}
	public void setArt_title(String art_title) {
		this.art_title = art_title;
	}
	public String getArt_date() {
		return art_date;
	}
	public void setArt_date(String art_date) {
		this.art_date = art_date;
	}
	public String getArt_image_url() {
		return art_image_url;
	}
	public void setArt_image_url(String art_image_url) {
		this.art_image_url = art_image_url;
	}
	public String getArt_mark() {
		return art_mark;
	}
	public void setArt_mark(String art_mark) {
		this.art_mark = art_mark;
	}
	
}
