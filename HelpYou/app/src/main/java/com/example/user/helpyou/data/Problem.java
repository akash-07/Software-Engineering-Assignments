package com.example.user.helpyou.data;

/**
 * Created by @K@sh on 8/16/2017.
 */

public class Problem {
    private String name;
    private String area;
    private String timeStamp;
    private String phNo;
    private Boolean status;
    private String problemTitle;
    private String probDescription;

    String getName()    {   return name;   }
    String getArea()    {   return area;    }
    String getTimeStamp()   {   return timeStamp;    }
    String getPhNo()    {   return  phNo;   }
    String getProblemTitle()    {   return problemTitle;    }
    String getProbDescription() {   return probDescription; }
    Boolean getStatus() {   return status;  }

    void setName(String name)   {   this.name = name;   }
    void setArea(String area)   {   this.area = area;   }
    void setTimeStamp(String timeStamp) {   this.timeStamp = timeStamp; }
    void setPhNo(String phNo)   {    this.phNo = phNo;   }
    void setStatus(Boolean status)  {   this.status = status;   }
    void setProblemTitle(String problemTitle)   {   this.problemTitle = problemTitle;   }
    void setProbDescription(String probDescription) {   this.probDescription = probDescription; }
}
