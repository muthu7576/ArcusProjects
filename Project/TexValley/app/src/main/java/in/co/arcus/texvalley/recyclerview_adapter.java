package in.co.arcus.texvalley;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.List;

public class recyclerview_adapter extends RecyclerView.Adapter<recyclerview_adapter.ViewHolder> {
    private Context context;
    private List<MyData> myData;

    public recyclerview_adapter(Context context, List<MyData> myData) {
        this.context = context;
        this.myData = myData;
    }

    /*private  String[] historydatascollctnarry;*/

    @Override
    public recyclerview_adapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutresorceiditems = R.layout.recyclertextviews;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View listitems = layoutInflater.inflate(layoutresorceiditems,viewGroup,shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(listitems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder historyviewholder, int position) {
        historyviewholder.mdate.setText(myData.get(position).getDate());
        historyviewholder.visitedbyuser.setText(myData.get(position).getVisitedusers());
        historyviewholder.modeofcntct.setText(myData.get(position).getModeofcntct());
        historyviewholder.stagesofuser.setText(myData.get(position).getStagesofhstry());
    }

    @Override
    public int getItemCount() {

        return  myData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public  final TextView mdate;
        public  final TextView modeofcntct;
        public  final TextView visitedbyuser;
        public  final TextView stagesofuser;
        public ViewHolder(View itemView) {
            super(itemView);
            mdate = (TextView)itemView.findViewById(R.id.text_timeline_date);
            modeofcntct = (TextView)itemView.findViewById(R.id.text_timeline_mdc);
            visitedbyuser = (TextView)itemView.findViewById(R.id.text_timeline_visitedby);
            stagesofuser = (TextView)itemView.findViewById(R.id.text_timeline_stages);
        }
    }
}
