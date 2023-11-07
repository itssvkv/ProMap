package com.itssvkv.promap.ui.mapimport android.os.Bundleimport android.util.Logimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport androidx.fragment.app.Fragmentimport com.google.android.gms.maps.CameraUpdateFactoryimport com.google.android.gms.maps.GoogleMapimport com.google.android.gms.maps.OnMapReadyCallbackimport com.google.android.gms.maps.SupportMapFragmentimport com.google.android.gms.maps.model.LatLngimport com.google.android.gms.maps.model.MapStyleOptionsimport com.google.android.gms.maps.model.MarkerOptionsimport com.itssvkv.promap.Rimport com.itssvkv.promap.databinding.FragmentMapBindingimport com.itssvkv.promap.ui.map.bottomsheets.CameraBottomSheetimport com.itssvkv.promap.ui.map.bottomsheets.SettingBottomSheetimport com.itssvkv.promap.utils.CameraUtils.animateToNairobiimport com.itssvkv.promap.utils.CameraUtils.moveCameraToBerlinimport com.itssvkv.promap.utils.CameraUtils.moveCameraToDenverBoundsAndRestrictimport com.itssvkv.promap.utils.CameraUtils.moveCameraToTokyoBoundsimport com.itssvkv.promap.utils.CameraUtils.setMaxZoomLevelimport com.itssvkv.promap.utils.CameraUtils.setMinZoomLevelimport com.itssvkv.promap.utils.CameraUtils.showBuildingimport com.itssvkv.promap.utils.Common.TAGclass MapFragment : Fragment(), OnMapReadyCallback {    private var binding: FragmentMapBinding? = null    private var map: GoogleMap? = null    private var isAllFABsVisible: Boolean? = null    private val settingBottomSheet: SettingBottomSheet by lazy { SettingBottomSheet() }    private val cameraBottomSheet: CameraBottomSheet by lazy { CameraBottomSheet() }    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        binding = FragmentMapBinding.inflate(layoutInflater, container, false)        return binding?.root    }    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        super.onViewCreated(view, savedInstanceState)        val mapFragment =            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment        mapFragment.getMapAsync(this)        initAddFAB()        initCheckBox()        initMapTypeClicks()        initMapStyleClicks()        initCameraShowBuilding()        setCameraZoomLevel()        initCameraBtnClicks()    }    private fun initAddFAB() {        binding?.settingFab?.visibility = View.GONE        binding?.cameraFab?.visibility = View.GONE        binding?.markerFab?.visibility = View.GONE        binding?.polylineFab?.visibility = View.GONE        binding?.clearFab?.visibility = View.GONE        isAllFABsVisible = false        binding?.addFab?.setOnClickListener {            isAllFABsVisible = if (!isAllFABsVisible!!) {                binding?.settingFab?.show()                binding?.cameraFab?.show()                binding?.markerFab?.show()                binding?.polylineFab?.show()                binding?.clearFab?.show()                true            } else {                binding?.settingFab?.hide()                binding?.cameraFab?.hide()                binding?.markerFab?.hide()                binding?.polylineFab?.hide()                binding?.clearFab?.hide()                false            }        }        initSettingFABClick()        initCameraFABClick()    }    private fun initSettingFABClick() {        binding?.settingFab?.setOnClickListener {            settingBottomSheet.show(requireActivity().supportFragmentManager, null)        }    }    private fun initCameraFABClick() {        binding?.cameraFab?.setOnClickListener {            cameraBottomSheet.show(requireActivity().supportFragmentManager, null)        }    }    override fun onMapReady(map: GoogleMap) {        this.map = map        val damietta = LatLng(31.41755371534545, 31.813648918328127)        map.addMarker(MarkerOptions().position(damietta).title("my location"))        map.moveCamera(CameraUpdateFactory.newLatLng(damietta))    }    private fun initCheckBox() {        settingBottomSheet.isZoomControlsChecked = {            map?.uiSettings?.isZoomControlsEnabled = it        }        settingBottomSheet.isZoomGesturesChecked = {            map?.uiSettings?.isZoomGesturesEnabled = it        }        settingBottomSheet.isScrollGesturesChecked = {            map?.uiSettings?.isScrollGesturesEnabled = it        }        settingBottomSheet.isRotateGesturesChecked = {            map?.uiSettings?.isRotateGesturesEnabled = it        }        settingBottomSheet.isCompassChecked = {            map?.uiSettings?.isCompassEnabled = it        }        settingBottomSheet.isToolbarChecked = {            map?.uiSettings?.isMapToolbarEnabled = it        }    }    private fun initMapTypeClicks() {        settingBottomSheet.mapType = {            when (it.id) {                1 -> map?.mapType = GoogleMap.MAP_TYPE_NORMAL                2 -> map?.mapType = GoogleMap.MAP_TYPE_SATELLITE                3 -> map?.mapType = GoogleMap.MAP_TYPE_TERRAIN                4 -> map?.mapType = GoogleMap.MAP_TYPE_HYBRID                5 -> map?.mapType = GoogleMap.MAP_TYPE_NONE                else -> map?.mapType = GoogleMap.MAP_TYPE_NORMAL            }        }    }    private fun initMapStyleClicks() {        settingBottomSheet.mapStyle = {            try {                map?.setMapStyle(                    MapStyleOptions.loadRawResourceStyle(                        requireContext(),                        it.path                    )                )            } catch (e: Exception) {                Log.d(TAG, "initMapStyleClicks: ${e.message}")            }        }    }    private fun initCameraShowBuilding() {        cameraBottomSheet.showBuildings = { isChecked ->            showBuilding(map, isChecked)        }    }    private fun setCameraZoomLevel() {        cameraBottomSheet.setMinZoomLevel = { minLevel ->            setMinZoomLevel(map, minLevel)        }        cameraBottomSheet.setMaxZoomLevel = { maxLevel ->            setMaxZoomLevel(map, maxLevel)        }    }    private fun initCameraBtnClicks() {        cameraBottomSheet.moveToBerlin = {            moveCameraToBerlin(map)        }        cameraBottomSheet.moveToTokyoBounds = {            moveCameraToTokyoBounds(map)        }        cameraBottomSheet.moveToDenverBoundsRestrict = {            moveCameraToDenverBoundsAndRestrict(map)        }        cameraBottomSheet.animateToNairobi = {            animateToNairobi(map)        }    }    override fun onDestroy() {        super.onDestroy()        binding = null        map = null    }}