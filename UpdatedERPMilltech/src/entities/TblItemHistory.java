package entities;

public class TblItemHistory {
	
	private String stock_description;
	private double jan;
	private double feb;
	private double mar;
	private double apr;
	private double may;
	private double jun;
	private double jul;
	private double aug;
	private double sep;
	private double oct;
	private double nov;
	private double dec;
	private double total;
	private double per_month;
	
	public TblItemHistory(String stock_description, double jan, double feb, double mar, double apr, double may, double jun,
			double jul, double aug, double sep, double oct, double nov, double dec, double total, double per_month) {
		super();
		this.stock_description = stock_description;
		this.jan = jan;
		this.feb = feb;
		this.mar = mar;
		this.apr = apr;
		this.may = may;
		this.jun = jun;
		this.jul = jul;
		this.aug = aug;
		this.sep = sep;
		this.oct = oct;
		this.nov = nov;
		this.dec = dec;
		this.total = total;
		this.per_month = per_month;
	}

	public String getstock_description() {
		return stock_description;
	}

	public void setstock_description(String stock_description) {
		this.stock_description = stock_description;
	}

	public double getJan() {
		return jan;
	}

	public void setJan(double jan) {
		this.jan = jan;
	}

	public double getFeb() {
		return feb;
	}

	public void setFeb(double feb) {
		this.feb = feb;
	}

	public double getMar() {
		return mar;
	}

	public void setMar(double mar) {
		this.mar = mar;
	}

	public double getApr() {
		return apr;
	}

	public void setApr(double apr) {
		this.apr = apr;
	}

	public double getMay() {
		return may;
	}

	public void setMay(double may) {
		this.may = may;
	}

	public double getJun() {
		return jun;
	}

	public void setJun(double jun) {
		this.jun = jun;
	}

	public double getJul() {
		return jul;
	}

	public void setJul(double jul) {
		this.jul = jul;
	}

	public double getAug() {
		return aug;
	}

	public void setAug(double aug) {
		this.aug = aug;
	}

	public double getSep() {
		return sep;
	}

	public void setSep(double sep) {
		this.sep = sep;
	}

	public double getOct() {
		return oct;
	}

	public void setOct(double oct) {
		this.oct = oct;
	}

	public double getNov() {
		return nov;
	}

	public void setNov(double nov) {
		this.nov = nov;
	}

	public double getDec() {
		return dec;
	}

	public void setDec(double dec) {
		this.dec = dec;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	public double getPer_month() {
		return per_month;
	}

	public void setPer_month(double per_month) {
		this.per_month = per_month;
	}
}