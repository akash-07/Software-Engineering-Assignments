package com.example.user.helpyou.data;

/**
 * Created by @K@sh on 8/16/2017.
 */

public class Problem {
    private String name;
    private String area;
    private String timeStamp;
    private String phNo;
    private String status;
    private String problemTitle;
    private String probDescription;

    public String getName()    {   return name;   }
    public String getArea()    {   return area;    }
    public String getTimeStamp()   {   return timeStamp;    }
    public String getPhNo()    {   return  phNo;   }
    public String getProblemTitle()    {   return problemTitle;    }
    public String getProbDescription() {   return probDescription; }
    public String getStatus() {   return status;  }

    public void setName(String name)   {   this.name = name;   }
    public void setArea(String area)   {   this.area = area;   }
    public void setTimeStamp(String timeStamp) {   this.timeStamp = timeStamp; }
    public void setPhNo(String phNo)   {    this.phNo = phNo;   }
    public void setStatus(String status)  {   this.status = status;   }
    public void setProblemTitle(String problemTitle)   {   this.problemTitle = problemTitle;   }
    public void setProbDescription(String probDescription) {   this.probDescription = probDescription; }
}
