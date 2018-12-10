package com.ust.ItemBatch;

import org.springframework.batch.item.ItemProcessor;

import com.ust.ItemBatch.pojo.ReceivedItems;

public class ItemBatchProcessor implements ItemProcessor<ReceivedItems, ReceivedItems>{

	public ReceivedItems process(ReceivedItems rcvItems) throws Exception {
		System.out.println(rcvItems);
		return rcvItems;
	}

}
