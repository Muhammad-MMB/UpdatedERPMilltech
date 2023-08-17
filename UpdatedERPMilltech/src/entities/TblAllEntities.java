package entities;

public class TblAllEntities
{
  private String entitiesName;
  private String entitiesValue;
  
  public TblAllEntities()
  {
    entitiesName = "";
    entitiesValue = "";
  }
  
  public TblAllEntities(String entitesName, String entitesValue)
  {
    entitiesName = entitesName;
    entitiesValue = entitesValue;
  }
  
  public String getEntitiesName()
  {
    return entitiesName;
  }
  
  public void setEntitiesName(String entitiesName)
  {
    this.entitiesName = entitiesName;
  }
  
  public String getEntitiesValue()
  {
    return entitiesValue;
  }
  
  public void setEntitiesValue(String entitiesValue)
  {
    this.entitiesValue = entitiesValue;
  }
}