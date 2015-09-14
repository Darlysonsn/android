package com.bruno.aplicacaoclient;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


	public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
	    private Itens[] itemsData;
	    private Context mContext;
	 
	    public Adapter(Context context, Itens[] itemsData) {
	    	this.mContext = context;
	        this.itemsData = itemsData;
	    }
	 
	    @Override
	    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
	                                                   int viewType) {
	     
	        View itemLayoutView = LayoutInflater.from(parent.getContext())
	                               .inflate(R.layout.item_view, null);
	 
        
	        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
	        return viewHolder;
	    }
	 
	    @Override
	    public void onBindViewHolder(ViewHolder viewHolder, int position) {
	         
	         
	        viewHolder.txtViewTitle.setText(itemsData[position].getTitle());
	        viewHolder.imgViewIcon.setImageResource(itemsData[position].getImageUrl());
	        viewHolder.setClickListener(new ItemClickListener() {
	            @Override
	            public void onClick(View view, int position, boolean isLongClick) {
	            	   Intent intent = new Intent(mContext, MeusTrajetos.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            	   intent.putExtra("cod_localizacao", itemsData[position].getCodLocalizacao());
	            	   mContext.startActivity(intent);
	            }
	        });
	        
	 
	 
	    }
	    
	    public interface ItemClickListener {
	        void onClick(View view, int position, boolean isLongClick);
	    }
	     
	    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	        
	        public TextView txtViewTitle;
	        public ImageView imgViewIcon;
	        private ItemClickListener clickListener;
	         
	        public ViewHolder(View itemLayoutView) {
	            super(itemLayoutView);
	            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
	            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
	            itemLayoutView.setOnClickListener(this);
	        }
	        
	        public void setClickListener(ItemClickListener itemClickListener) {
	            this.clickListener = itemClickListener;
	        }
	        
	        @Override
	        public void onClick(View view) {
	            clickListener.onClick(view, getPosition(), false);
	            
	        }

	    }
	 
	 
	    @Override
	    public int getItemCount() {
	        return itemsData.length;
	    }
	

}
