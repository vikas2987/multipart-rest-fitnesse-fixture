import fit.Fixture;

public class XMLPageNumTest extends fit.Fixture
{
	private int httpStatus, maxRange, count, rangeStart, rangeSize, pageNum, rangeSt, rangeSi, pageN, countN, b;
	private String httpStatusFinal, maxRangeFinal, rangeStartFinal, rangeSizeFinal, pageNumFinal, countFinal;
	public void getHttpStatus(String a)
	{
		httpStatusFinal = getStringVal(a);
		httpStatus = Integer.parseInt(httpStatusFinal);
	}
	public void getMaxRange(String a)
	{
		maxRangeFinal = getStringVal(a);
		maxRange = Integer.parseInt(maxRangeFinal);
	}
	public void getRangeStart(String a)
	{
		rangeStartFinal=getStringVal(a);
		rangeStart = Integer.parseInt(rangeStartFinal);
	}
	public void getRangeSize(String a)
	{
		rangeSizeFinal=getStringVal(a);
		rangeSize = Integer.parseInt(rangeSizeFinal);
	}
	public void getPageNumber(String a)
	{
		pageNumFinal=getStringVal(a);
		pageNum = Integer.parseInt(pageNumFinal);
	}
	public void getCount(String a)
	{
		countFinal=getStringVal(a);
		count = Integer.parseInt(countFinal);
	}
	public String getStringVal(String val)
	{
		    String newVal = "";
		    if (Fixture.hasSymbol(val))
		    {
		        newVal = Fixture.getSymbol(val).toString();
		    }
		return newVal;
	}
	public boolean RangeSize()
	{
		boolean var = false;
			if(maxRange==0)
			{
				if(httpStatus==200)
					rangeSi=10;
				else if(httpStatus!=200)
					rangeSi = 0;
			}
			else if(maxRange!=0)
				rangeSi = 10;
			if(rangeSi == rangeSize)
				var = true;
		return var;
	}
	public boolean PageNumber()
	{
		boolean var = false;
			if(maxRange==0)
			{
				if(httpStatus==200)
					pageN=pageNum;
				else if(httpStatus!=200)
					pageN = 0;
			}
			else if(maxRange!=0)
				pageN = pageNum;
			if(pageN == pageNum)
				var = true;
		return var;
	}
	public boolean RangeStart()
	{
		boolean var = false;
			if(maxRange==0)
			{
				if(httpStatus==200)
				{
					b=--pageN;
					rangeSt=b*rangeSi;
				}
				else if(httpStatus!=200)
					rangeSt = 0;
			}
			else if( maxRange!=0)
			{
				b=--pageN;
				rangeSt=b*rangeSi;
			}
			if(rangeSt == rangeStart)
				var = true;
		return var;
	}
	public boolean Count()
	{
		boolean var = false;
		if(maxRange==0)
			countN=0;
		else if(maxRange>0&&rangeSt<=0)
		{
			if(maxRange<=rangeSi)
				countN=maxRange;
			else if(maxRange>rangeSi)
				countN=rangeSi;
		}
		else if(maxRange>0&&rangeSt>0)
		{
			if(maxRange<=rangeSi)
			{
				if(maxRange<=rangeSt)
					countN=0;
				else if(maxRange>rangeSt)
				{
					int temp1=maxRange-rangeSt;
					if(temp1>rangeSi)
						countN=rangeSi;
					else if(temp1<=rangeSi)
						countN=temp1;
				}
			}
			else if(maxRange>rangeSi)
			{
				if(rangeSt>=maxRange)
					countN=0;
				else if(rangeSt<maxRange&&rangeSt>rangeSi)
				{
					int temp2=maxRange-rangeSt;
					if(temp2>rangeSi)
						countN=rangeSi;
					else if(temp2<=rangeSi)
						countN=temp2;
				}
				else if(rangeSt<maxRange&&rangeSt<rangeSi)
				{
					int temp3=maxRange-rangeSt;
					if(temp3>=rangeSi)
						countN=rangeSi;
					else if(temp3<rangeSi)
						countN=temp3;
				}
				else if(rangeSt<maxRange&&rangeSt==rangeSi)
				{
					int temp4=maxRange-rangeSt;
					if(temp4>=rangeSi)
						countN=rangeSi;
					else if(temp4<rangeSi)
						countN=temp4;
				}
			}
		}
		if(countN == count)
			var = true;
		return var;
	}
}
