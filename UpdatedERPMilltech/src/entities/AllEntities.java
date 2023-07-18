package entities;

public class AllEntities
{
  private String entitiesName;
  private String entitiesValue;
  
  public AllEntities()
  {
    entitiesName = "";
    entitiesValue = "";
  }
  
  public AllEntities(String entitesName, String entitesValue)
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