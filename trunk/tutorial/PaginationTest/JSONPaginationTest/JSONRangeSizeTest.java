import fit.Fixture;

public class JSONRangeSizeTest extends fit.Fixture
{
	private float httpStatus, maxRange, count, rangeStart, rangeSize, pageNum, rangeSt, rangeSi, pageN, countN;
	private String httpStatusFinal, maxRangeFinal, rangeStartFinal, rangeSizeFinal, pageNumFinal, countFinal;
	public void getHttpStatus(String a)
	{
		httpStatusFinal = getStringVal(a);
		httpStatus = Float.parseFloat(httpStatusFinal);
	}
	public void getMaxRange(String a)
	{
		maxRangeFinal = getStringVal(a);
		maxRange = Float.parseFloat(maxRangeFinal);
	}
	public void getRangeStart(String a)
	{
		rangeStartFinal=getStringVal(a);
		rangeStart = Float.parseFloat(rangeStartFinal);
	}
	public void getRangeSize(String a)
	{
		rangeSizeFinal=getStringVal(a);
		rangeSize = Float.parseFloat(rangeSizeFinal);
	}
	public void getPageNumber(String a)
	{
		pageNumFinal=getStringVal(a);
		pageNum = Float.parseFloat(pageNumFinal);
	}
	public void getCount(String a)
	{
		countFinal=getStringVal(a);
		count = Float.parseFloat(countFinal);
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
	public boolean RangeStart()
	{
		boolean var = false;
			if(maxRange==0)
			{
				if(httpStatus==200)
					rangeSt = 0;
				else if(httpStatus!=200)
					rangeSt = 0;
			}
			else if( maxRange!=0)
				rangeSt = 0;
			if(rangeSt == rangeStart)
				var = true;
		return var;
	}
	public boolean RangeSize()
	{
		boolean var = false;
			if(maxRange==0)
			{
				if(httpStatus==200)
					rangeSi = rangeSize;
				else if(httpStatus!=200)
					rangeSi=0;
			}
			else if(maxRange!=0)
				rangeSi = rangeSize;
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
					pageN=1;
				else if(httpStatus!=200)
					pageN = 0;
			}
			else if(maxRange!=0)
				pageN = 1;
			if(pageN == pageNum)
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
					float temp1=maxRange-rangeSt;
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
					float temp2=maxRange-rangeSt;
					if(temp2>rangeSi)
						countN=rangeSi;
					else if(temp2<=rangeSi)
						countN=temp2;
				}
				else if(rangeSt<maxRange&&rangeSt<rangeSi)
				{
					float temp3=maxRange-rangeSt;
					if(temp3>=rangeSi)
						countN=rangeSi;
					else if(temp3<rangeSi)
						countN=temp3;
				}
				else if(rangeSt<maxRange&&rangeSt==rangeSi)
				{
					float temp4=maxRange-rangeSt;
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
