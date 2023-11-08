package com.itssvkv.promap.ui.map.bottomsheetsimport android.os.Bundleimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport com.google.android.material.bottomsheet.BottomSheetDialogFragmentimport com.itssvkv.promap.Rimport com.itssvkv.promap.databinding.MarkerBottomSheetBindingclass MarkerBottomSheet : BottomSheetDialogFragment() {    private var binding: MarkerBottomSheetBinding? = null    var addMarkerOnHelsinki: (() -> Unit)? = null    var addCustomMarkerOnGaza: (() -> Unit)? = null    var addCustomMarkerOnDamietta: (() -> Unit)? = null    var removeAllMarkers: (() -> Unit)? = null    var enableDragCheck: ((Boolean) -> Unit)? = null    var enableCustomInfoWindowChick: ((Boolean) -> Unit)? = null    init {        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)    }    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        binding = MarkerBottomSheetBinding.inflate(layoutInflater, container, false)        initBtnClicks()        initCheckBox()        return binding?.root    }    private fun initBtnClicks() {        binding?.helsinkiBtn?.setOnClickListener {            addMarkerOnHelsinki?.invoke()            this.dismiss()        }        binding?.gazaBtn?.setOnClickListener {            addCustomMarkerOnGaza?.invoke()            this.dismiss()        }        binding?.damiettaBtn?.setOnClickListener {            addCustomMarkerOnDamietta?.invoke()            this.dismiss()        }        binding?.clearBtn?.setOnClickListener {            removeAllMarkers?.invoke()            this.dismiss()        }    }    private fun initCheckBox() {        binding?.enableDrag?.setOnCheckedChangeListener { _, isChecked ->            enableDragCheck?.invoke(isChecked)        }        binding?.customWindow?.setOnCheckedChangeListener { _, isChecked ->            enableCustomInfoWindowChick?.invoke(isChecked)        }    }    override fun onDestroy() {        super.onDestroy()        binding = null    }}