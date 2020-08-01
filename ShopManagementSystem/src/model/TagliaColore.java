package model;


public class TagliaColore extends Modello {
			private String ean;
		private String colore;
		private int giacenza;
		private String taglia;
		public TagliaColore() {
			
		}
		/**
		 * @param ean
		 * @param colore
		 * @param giacenza
		 * @param taglia
		 */
		public TagliaColore(String ean, String colore, int giacenza, String taglia) {
			this.ean = ean;
			this.colore = colore;
			this.giacenza = giacenza;
			this.taglia = taglia;
		}
		/**
		 * @return the ean
		 */
		public String getEan() {
			return ean;
		}
		/**
		 * @param ean the ean to set
		 */
		public void setEan(String ean) {
			this.ean = ean;
		}
		
		/**
		 * @return the colore
		 */
		public String getColore() {
			return colore;
		}
		/**
		 * @param colore the colore to set
		 */
		public void setColore(String colore) {
			this.colore = colore;
		}
		
		/**
		 * @return the giacenza
		 */
		public int getGiacenza() {
			return giacenza;
		}
		/**
		 * @param giacenza the giacenza to set
		 */
		public void setGiacenza(int giacenza) {
			this.giacenza = giacenza;
		}
		
		/**
		 * @return the taglia
		 */
		public String getTaglia() {
			return taglia;
		}
		/**
		 * @param taglia the taglia to set
		 */
		public void setTaglia(String taglia) {
			this.taglia = taglia;
		}
		
}
