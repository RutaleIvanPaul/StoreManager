package com.kotlin.ivanpaulrutale.storemanager.views

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.kotlin.ivanpaulrutale.storemanager.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ART_NUMBER = "param1"
private const val COLOR = "param2"
private const val DESCRIPTION = "param3"
private const val STORE = "param4"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CheckOut.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CheckOut.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckOut : Fragment() {
    // TODO: Rename and change types of parameters
    private var art_number: String? = null
    private var color: String? = null
    private var description: String? = null
    private var store: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            art_number = it.getString(ART_NUMBER)
            color = it.getString(COLOR)
            description = it.getString(DESCRIPTION)
            store = it.getString(STORE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check_out, container, false)
        if (art_number != null){
            view.findViewById<TextInputEditText>(R.id.checkout_art_number).setText(art_number)
            view.findViewById<TextInputEditText>(R.id.checkout_color).setText(color)
            view.findViewById<TextInputEditText>(R.id.checkout_description).setText(description)
            view.findViewById<TextInputEditText>(R.id.checkout_store).setText(store)
        }

        view.findViewById<Button>(R.id.checkout_button).setOnClickListener {
            val art_number = view.findViewById<EditText>(R.id.checkout_art_number)
            val color = view.findViewById<EditText>(R.id.checkout_color)
            val description = view.findViewById<EditText>(R.id.checkout_description)
            val quantity = view.findViewById<EditText>(R.id.checkout_quantity)
            val store = view.findViewById<EditText>(R.id.checkout_store)
            val collector = view.findViewById<EditText>(R.id.checkout_collector)

            val editTexts = arrayListOf<EditText>(art_number,color,description,quantity,store,collector)

            if (noEmptyFields(editTexts)){

                Toast.makeText(context,art_number.text,Toast.LENGTH_LONG).show()
                changeFromFragmentToFragment(activity,Search())
            }

        }

        // Inflate the layout for this fragment
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param art_number Parameter 1.
         * @param color Parameter 2.
         * @return A new instance of fragment CheckOut.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(art_number: String, color: String, description: String, store: String) =
            CheckOut().apply {
                arguments = Bundle().apply {
                    putString(ART_NUMBER, art_number)
                    putString(COLOR, color)
                    putString(DESCRIPTION, description)
                    putString(STORE, store)
                }
            }
    }
}
