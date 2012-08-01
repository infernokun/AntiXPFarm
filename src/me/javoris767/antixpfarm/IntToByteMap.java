package me.javoris767.antixpfarm;

public class IntToByteMap
{
	 private static final int MAX_ROW = 5;
	  int[][] keys;
	  byte[][] values;
	  
	  public IntToByteMap()
	  {
	    keys  = new int[10][];
	    values = new byte[10][];
	  }

	  private void expand()
	  {
	    int[][] oldKeys = keys;
	    byte[][] oldValues = values;
	    keys = new int[keys.length * 2][];
	    values = new byte[keys.length * 2][];
	    for (int i = 0; i < keys.length; i++) {
	      for (int j = 0; j < keys[i].length; j++) {
	        put(oldKeys[i][j], oldValues[i][j]);
	      }
	    }
	  }

	  public void put(int k, byte v)
	  {
	    int idx = k % keys.length;
	    int[] keySet = keys[idx];
	    if(keySet == null)
	    {
	      keySet = keys[idx] = new int[0];
	      values[idx] = new byte[0];
	    }
	    for(int i = 0; i < keySet.length; i++)
	    {
	      if(keySet[i] == k)
	      {
	        values[idx][i] = v;
	        return;
	      }
	    }

	    int[] newKeys = new int[keySet.length + 1];
	    newKeys[0] = k;
	    System.arraycopy(keySet, 0, newKeys, 1, keySet.length);
	    keys[idx] = newKeys;

	    byte[] newValues = new byte[values[idx].length + 1];
	    newValues[0] = v;
	    System.arraycopy(values[idx], 0, newValues, 1, values[idx].length);
	    values[idx] = newValues;

	    if(newKeys.length == MAX_ROW)
	      expand();

	  }

	  public Byte get(int k)
	  {
	    int idx = k % keys.length;
	    int[] keySet = keys[idx];
	    if(keySet != null)
	    {
	      byte[] valueSet = values[idx];
	      for (int i = 0; i < keySet.length; i++) {
	        if(keySet[i] == k)
	          return valueSet[i];
	      }
	    }
	    return null;
	  }

	  public Byte remove(int k) {
	    int idx = k % keys.length;
	    int[] keySet = keys[idx];
	    byte[] valSet = values[idx];
	    for(int i = 0; i < keySet.length; i++)
	    {
	      if(keySet[i] == k)
	      {
	        byte b = valSet[i];
	        keys[idx] = new int[keySet.length - 1];
	        values[idx] = new byte[valSet.length - 1];
	        System.arraycopy(keySet, 0, keys[idx], 0, i);
	        System.arraycopy(keySet, i + 1, keys[idx], i, keySet.length - i - 1);
	        
	        System.arraycopy(valSet, 0, values[idx], 0, i);
	        System.arraycopy(valSet, i + 1, values[idx], i, valSet.length - i - 1);
	        
	        return b;
	      }
	    }
	    return null;
	  }
	  
	  public static void main(String[] args)
	  {
	    /* test */
	    IntToByteMap testMap = new IntToByteMap();
	    testMap.put(15, (byte)2);
	    testMap.put(5, (byte)4);
	    testMap.put(25, (byte)3);
	    
	    testMap.remove(15);
	    
	    //testMap.put(2, (byte)(testMap.get(2) + 1));
	    
	    System.out.println(testMap.get(15));
	    System.out.println(testMap.get(5));
	    System.out.println(testMap.get(25));
	  }
	}