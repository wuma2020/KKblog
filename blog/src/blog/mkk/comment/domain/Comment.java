package blog.mkk.comment.domain;

public class Comment {
		private int com_id;//评论id
		private String art_id;//文章的id（该留言是该文章的留言）
		private String com_name;//评论者名称
		private String com_content;//评论内容
		private String com_date;//评论时时间
		private String com_pid;//父评论
		private String com_type;//评论的类型           0：小学区          1：初中区   2：高中区    3：大学区   4:其他
		
		public int getCom_id() {
			return com_id;
		}
		public void setCom_id(int com_id) {
			this.com_id = com_id;
		}
		public String getCom_name() {
			return com_name;
		}
		public void setCom_name(String com_name) {
			this.com_name = com_name;
		}
		public String getCom_content() {
			return com_content;
		}
		public void setCom_content(String com_content) {
			this.com_content = com_content;
		}
		public String getCom_date() {
			return com_date;
		}
		public void setCom_date(String com_date) {
			this.com_date = com_date;
		}
		public String getCom_pid() {
			return com_pid;
		}
		public void setCom_pid(String com_pid) {
			this.com_pid = com_pid;
		}
		public String getArt_id() {
			return art_id;
		}
		public void setArt_id(String art_id) {
			this.art_id = art_id;
		}
		public String getCom_type() {
			return com_type;
		}
		public void setCom_type(String com_type) {
			this.com_type = com_type;
		}
	
		
		
}
