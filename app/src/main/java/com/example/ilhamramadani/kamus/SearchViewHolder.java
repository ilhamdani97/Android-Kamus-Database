package com.example.ilhamramadani.kamus;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

class SearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_word)
    TextView tv_word;

    @BindView(R.id.tv_translate)
    TextView tv_translate;

    public SearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final KamusModel item) {
        tv_word.setText(item.getWord());
        tv_translate.setText(item.getTranslate());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.ITEM_WORD, item.getWord());
                intent.putExtra(DetailActivity.ITEM_TRANSLATE, item.getTranslate());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
