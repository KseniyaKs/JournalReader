package com.example.articlestest.presentation.registration.user_city


//class FilteredArrayAdapter constructor(context: Context, val list: List<City>) :
//    ArrayAdapter<City>(context, 0, list) {
//    private var filter: Filter = AppFilter(list)
//
//    override fun getCount(): Int {
//        return list.size
//    }
//
//    override fun getItem(position: Int): City {
//        return list[position]
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//
//        var currentItemView = convertView
//
//        if (currentItemView == null) {
//            currentItemView = LayoutInflater.from(context).inflate(
//                R.layout.city_item, parent, false
//            )
//        }

//        currentItemView!!.findViewById<TextView>(R.id.txt_city_name).text = list[position].name

//        return currentItemView
//    }

//    override fun getFilter(): Filter {
//        return AppFilter(list)
//    }
//
//    private inner class AppFilter(private val filterList: List<City>) : Filter() {
//
//        override fun performFiltering(constraint: CharSequence?): FilterResults {
//            val filterSeq = constraint.toString().lowercase(Locale.getDefault())
//            val result = FilterResults()
//            if (filterSeq.isNotEmpty()) {
//                val filter = ArrayList<City>()
//                for (city in filterList) {
//                    if (city.name.lowercase(Locale.getDefault())
//                            .contains(filterSeq)
//                    ) filter.add(city)
//                }
//                result.count = filter.size
//                result.values = filter
//            } else {
//                result.values = filterList
//                result.count = filterList.size
//            }
//            return result
//        }
//
//        override fun publishResults(
//            constraint: CharSequence?,
//            results: FilterResults
//        ) {
//            val filtered = results.values as ArrayList<City>
//            notifyDataSetChanged()
//            clear()
//            var i = 0
//            val l: Int = filtered.size
//            while (i < l) {
//                add(filtered[i])
//                i++
//            }
//            notifyDataSetInvalidated()
//        }
//    }
//}