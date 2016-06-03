package org.appsroid.fxpro.bitmap;//####[19]####
//####[19]####
import java.util.ArrayList;//####[21]####
import android.os.Bundle;//####[22]####
import android.os.Handler;//####[23]####
import android.os.Looper;//####[24]####
import pj.parser.ast.visitor.DummyClassToDetermineVariableType;//####[25]####
import pt.runtime.*;//####[26]####
import pj.Pyjama;//####[27]####
import pj.PJPackageOnly;//####[28]####
import pj.UniqueThreadIdGeneratorForOpenMP;//####[29]####
import pi.ParIteratorFactory;//####[30]####
import pi.ParIterator;//####[31]####
import pi.reductions.Reducible;//####[32]####
import pi.reductions.Reduction;//####[33]####
import java.util.concurrent.atomic.*;//####[34]####
import java.util.concurrent.*;//####[35]####
import java.util.concurrent.ExecutorService;//####[36]####
import java.util.concurrent.Executors;//####[37]####
import java.util.concurrent.TimeUnit;//####[38]####
import pj.parser.ast.visitor.DummyClassToDetermineVariableType;//####[39]####
import java.io.IOException;//####[40]####
import java.util.Random;//####[41]####
import android.graphics.Bitmap;//####[42]####
import android.graphics.Canvas;//####[43]####
import android.graphics.Color;//####[44]####
import android.graphics.ColorFilter;//####[45]####
import android.graphics.ColorMatrix;//####[46]####
import android.graphics.ColorMatrixColorFilter;//####[47]####
import android.graphics.LightingColorFilter;//####[48]####
import android.graphics.Matrix;//####[49]####
import android.graphics.Paint;//####[50]####
import android.graphics.PorterDuff;//####[51]####
import android.graphics.PorterDuffXfermode;//####[52]####
import android.graphics.RadialGradient;//####[53]####
import android.graphics.Rect;//####[54]####
import android.graphics.RectF;//####[55]####
import android.graphics.Shader;//####[56]####
import android.media.ExifInterface;//####[57]####
import pi.reductions.Reducible;//####[59]####
import java.util.*;//####[60]####
//####[60]####
//-- ParaTask related imports//####[60]####
import pt.runtime.*;//####[60]####
import java.util.concurrent.ExecutionException;//####[60]####
import java.util.concurrent.locks.*;//####[60]####
import java.lang.reflect.*;//####[60]####
import pt.runtime.GuiThread;//####[60]####
import java.util.concurrent.BlockingQueue;//####[60]####
import java.util.ArrayList;//####[60]####
import java.util.List;//####[60]####
//####[60]####
public class BitmapProcessing {//####[61]####
    static{ParaTask.init();}//####[61]####
    /*  ParaTask helper method to access private/protected slots *///####[61]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[61]####
        if (m.getParameterTypes().length == 0)//####[61]####
            m.invoke(instance);//####[61]####
        else if ((m.getParameterTypes().length == 1))//####[61]####
            m.invoke(instance, arg);//####[61]####
        else //####[61]####
            m.invoke(instance, arg, interResult);//####[61]####
    }//####[61]####
//####[63]####
    static {//####[63]####
        try {//####[64]####
            Pyjama.init();//####[65]####
        } catch (Exception e) {//####[66]####
        }//####[67]####
    }//####[68]####
//####[69]####
    private static ArrayList<ParIterator<?>> _omp_piVarContainer = new ArrayList<ParIterator<?>>();//####[69]####
//####[70]####
    private static AtomicBoolean _holderForPIFirst;//####[70]####
//####[72]####
    Handler _omp_android_mainLoopHandler = new Handler(Looper.getMainLooper());//####[72]####
//####[73]####
    public static Bitmap rotate(Bitmap bitmap, float degrees) {//####[73]####
        {//####[73]####
            Matrix matrix = new Matrix();//####[74]####
            matrix.postRotate(degrees);//####[75]####
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);//####[76]####
        }//####[77]####
    }//####[78]####
//####[80]####
    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {//####[80]####
        {//####[80]####
            Matrix matrix = new Matrix();//####[81]####
            matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);//####[82]####
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);//####[83]####
        }//####[84]####
    }//####[85]####
//####[87]####
    public static Bitmap emboss(Bitmap src) {//####[87]####
        {//####[87]####
            double[][] EmbossConfig = new double[][] { { -1, 0, -1 }, { 0, 4, 0 }, { -1, 0, -1 } };//####[88]####
            ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);//####[89]####
            convMatrix.applyConfig(EmbossConfig);//####[90]####
            convMatrix.Factor = 1;//####[91]####
            convMatrix.Offset = 127;//####[92]####
            return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);//####[93]####
        }//####[94]####
    }//####[95]####
//####[97]####
    public static Bitmap cfilter(Bitmap src, double red, double green, double blue) {//####[97]####
        {//####[97]####
            red = (double) red / 100;//####[98]####
            green = (double) green / 100;//####[99]####
            blue = (double) blue / 100;//####[100]####
            int width = src.getWidth();//####[101]####
            int height = src.getHeight();//####[102]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[103]####
            int A = 0, R = 0, G = 0, B = 0;//####[104]####
            int pixel = 0;//####[105]####
            if (Pyjama.insideParallelRegion()) //####[107]####
            {//####[107]####
                {//####[109]####
                    for (int x = 0; x < width; x = x + 1) //####[110]####
                    {//####[110]####
                        for (int y = 0; y < height; ++y) //####[111]####
                        {//####[111]####
                            pixel = src.getPixel(x, y);//####[112]####
                            A = Color.alpha(pixel);//####[113]####
                            R = (int) (Color.red(pixel) * red);//####[114]####
                            G = (int) (Color.green(pixel) * green);//####[115]####
                            B = (int) (Color.blue(pixel) * blue);//####[116]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[117]####
                        }//####[118]####
                    }//####[119]####
                }//####[120]####
            } else {//####[121]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[123]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing0 _omp__parallelRegionVarHolderInstance_0 = new _omp__parallelRegionVarHolderClass_BitmapProcessing0();//####[126]####
                _omp__parallelRegionVarHolderInstance_0.bmOut = bmOut;//####[127]####
                _omp__parallelRegionVarHolderInstance_0.G = G;//####[128]####
                _omp__parallelRegionVarHolderInstance_0.height = height;//####[129]####
                _omp__parallelRegionVarHolderInstance_0.red = red;//####[130]####
                _omp__parallelRegionVarHolderInstance_0.pixel = pixel;//####[131]####
                _omp__parallelRegionVarHolderInstance_0.A = A;//####[132]####
                _omp__parallelRegionVarHolderInstance_0.blue = blue;//####[133]####
                _omp__parallelRegionVarHolderInstance_0.B = B;//####[134]####
                _omp__parallelRegionVarHolderInstance_0.green = green;//####[135]####
                _omp__parallelRegionVarHolderInstance_0.R = R;//####[136]####
                _omp__parallelRegionVarHolderInstance_0.width = width;//####[137]####
                _omp__parallelRegionVarHolderInstance_0.src = src;//####[138]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[141]####
                TaskID _omp__parallelRegionTaskID_0 = _ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[142]####
                __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[143]####
                try {//####[144]####
                    _omp__parallelRegionTaskID_0.waitTillFinished();//####[144]####
                } catch (Exception __pt__ex) {//####[144]####
                    __pt__ex.printStackTrace();//####[144]####
                }//####[144]####
                PJPackageOnly.setMasterThread(null);//####[146]####
                _holderForPIFirst.set(true);//####[147]####
                bmOut = _omp__parallelRegionVarHolderInstance_0.bmOut;//####[149]####
                G = _omp__parallelRegionVarHolderInstance_0.G;//####[150]####
                height = _omp__parallelRegionVarHolderInstance_0.height;//####[151]####
                red = _omp__parallelRegionVarHolderInstance_0.red;//####[152]####
                pixel = _omp__parallelRegionVarHolderInstance_0.pixel;//####[153]####
                A = _omp__parallelRegionVarHolderInstance_0.A;//####[154]####
                blue = _omp__parallelRegionVarHolderInstance_0.blue;//####[155]####
                B = _omp__parallelRegionVarHolderInstance_0.B;//####[156]####
                green = _omp__parallelRegionVarHolderInstance_0.green;//####[157]####
                R = _omp__parallelRegionVarHolderInstance_0.R;//####[158]####
                width = _omp__parallelRegionVarHolderInstance_0.width;//####[159]####
                src = _omp__parallelRegionVarHolderInstance_0.src;//####[160]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[161]####
            }//####[162]####
            src.recycle();//####[165]####
            src = null;//####[166]####
            return bmOut;//####[167]####
        }//####[168]####
    }//####[169]####
//####[170]####
    private static AtomicBoolean _imFirst_2 = new AtomicBoolean(true);//####[170]####
//####[171]####
    private static AtomicInteger _imFinishedCounter_2 = new AtomicInteger(0);//####[171]####
//####[172]####
    private static CountDownLatch _waitBarrier_2 = new CountDownLatch(1);//####[172]####
//####[173]####
    private static CountDownLatch _waitBarrierAfter_2 = new CountDownLatch(1);//####[173]####
//####[174]####
    private static ParIterator<Integer> _pi_2 = null;//####[174]####
//####[175]####
    private static Integer _lastElement_2 = null;//####[175]####
//####[176]####
    private static _ompWorkSharedUserCode_BitmapProcessing2_variables _ompWorkSharedUserCode_BitmapProcessing2_variables_instance = null;//####[176]####
//####[177]####
    private static void _ompWorkSharedUserCode_BitmapProcessing2(_ompWorkSharedUserCode_BitmapProcessing2_variables __omp_vars) {//####[177]####
        int width = __omp_vars.width;//####[179]####
        int height = __omp_vars.height;//####[180]####
        int A = __omp_vars.A;//####[181]####
        int R = __omp_vars.R;//####[182]####
        int G = __omp_vars.G;//####[183]####
        int B = __omp_vars.B;//####[184]####
        int pixel = __omp_vars.pixel;//####[185]####
        double red = __omp_vars.red;//####[186]####
        double green = __omp_vars.green;//####[187]####
        double blue = __omp_vars.blue;//####[188]####
        Bitmap bmOut = __omp_vars.bmOut;//####[189]####
        Bitmap src = __omp_vars.src;//####[190]####
        Integer x;//####[191]####
        while (_pi_2.hasNext()) //####[192]####
        {//####[192]####
            x = _pi_2.next();//####[193]####
            {//####[195]####
                for (int y = 0; y < height; ++y) //####[196]####
                {//####[196]####
                    pixel = src.getPixel(x, y);//####[197]####
                    A = Color.alpha(pixel);//####[198]####
                    R = (int) (Color.red(pixel) * red);//####[199]####
                    G = (int) (Color.green(pixel) * green);//####[200]####
                    B = (int) (Color.blue(pixel) * blue);//####[201]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[202]####
                }//####[203]####
            }//####[204]####
        }//####[205]####
        __omp_vars.src = src;//####[207]####
    }//####[208]####
//####[212]####
    private static volatile Method __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method = null;//####[212]####
    private synchronized static void __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_ensureMethodVarSet() {//####[212]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method == null) {//####[212]####
            try {//####[212]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_0", new Class[] {//####[212]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing0.class//####[212]####
                });//####[212]####
            } catch (Exception e) {//####[212]####
                e.printStackTrace();//####[212]####
            }//####[212]####
        }//####[212]####
    }//####[212]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_BitmapProcessing0 __omp_vars) {//####[212]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[212]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[212]####
    }//####[212]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_BitmapProcessing0 __omp_vars, TaskInfo taskinfo) {//####[212]####
        // ensure Method variable is set//####[212]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method == null) {//####[212]####
            __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_ensureMethodVarSet();//####[212]####
        }//####[212]####
        taskinfo.setParameters(__omp_vars);//####[212]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method);//####[212]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[212]####
    }//####[212]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing0> __omp_vars) {//####[212]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[212]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[212]####
    }//####[212]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing0> __omp_vars, TaskInfo taskinfo) {//####[212]####
        // ensure Method variable is set//####[212]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method == null) {//####[212]####
            __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_ensureMethodVarSet();//####[212]####
        }//####[212]####
        taskinfo.setTaskIdArgIndexes(0);//####[212]####
        taskinfo.addDependsOn(__omp_vars);//####[212]####
        taskinfo.setParameters(__omp_vars);//####[212]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method);//####[212]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[212]####
    }//####[212]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing0> __omp_vars) {//####[212]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[212]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[212]####
    }//####[212]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing0> __omp_vars, TaskInfo taskinfo) {//####[212]####
        // ensure Method variable is set//####[212]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method == null) {//####[212]####
            __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_ensureMethodVarSet();//####[212]####
        }//####[212]####
        taskinfo.setQueueArgIndexes(0);//####[212]####
        taskinfo.setIsPipeline(true);//####[212]####
        taskinfo.setParameters(__omp_vars);//####[212]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method);//####[212]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[212]####
    }//####[212]####
    public static void __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderClass_BitmapProcessing0 __omp_vars) {//####[212]####
        Bitmap bmOut = __omp_vars.bmOut;//####[214]####
        int G = __omp_vars.G;//####[215]####
        int height = __omp_vars.height;//####[216]####
        double red = __omp_vars.red;//####[217]####
        int pixel = __omp_vars.pixel;//####[218]####
        int A = __omp_vars.A;//####[219]####
        double blue = __omp_vars.blue;//####[220]####
        int B = __omp_vars.B;//####[221]####
        double green = __omp_vars.green;//####[222]####
        int R = __omp_vars.R;//####[223]####
        int width = __omp_vars.width;//####[224]####
        Bitmap src = __omp_vars.src;//####[225]####
        {//####[226]####
            if (Pyjama.insideParallelRegion()) //####[227]####
            {//####[227]####
                boolean _omp_imFirst = _imFirst_2.getAndSet(false);//####[229]####
                _holderForPIFirst = _imFirst_2;//####[230]####
                if (_omp_imFirst) //####[231]####
                {//####[231]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing2_variables();//####[232]####
                    int __omp_size_ = 0;//####[233]####
                    for (int x = 0; x < width; x = x + 1) //####[235]####
                    {//####[235]####
                        _lastElement_2 = x;//####[236]####
                        __omp_size_++;//####[237]####
                    }//####[238]####
                    _pi_2 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[239]####
                    _omp_piVarContainer.add(_pi_2);//####[240]####
                    _pi_2.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[241]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.width = width;//####[243]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.height = height;//####[244]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.A = A;//####[245]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.R = R;//####[246]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.G = G;//####[247]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.B = B;//####[248]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.pixel = pixel;//####[249]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.red = red;//####[250]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.green = green;//####[251]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.blue = blue;//####[252]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.bmOut = bmOut;//####[253]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.src = src;//####[254]####
                    _waitBarrier_2.countDown();//####[255]####
                } else {//####[256]####
                    try {//####[257]####
                        _waitBarrier_2.await();//####[257]####
                    } catch (InterruptedException __omp__ie) {//####[257]####
                        __omp__ie.printStackTrace();//####[257]####
                    }//####[257]####
                }//####[258]####
                _ompWorkSharedUserCode_BitmapProcessing2(_ompWorkSharedUserCode_BitmapProcessing2_variables_instance);//####[259]####
                if (_imFinishedCounter_2.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[260]####
                {//####[260]####
                    _waitBarrierAfter_2.countDown();//####[261]####
                } else {//####[262]####
                    try {//####[263]####
                        _waitBarrierAfter_2.await();//####[264]####
                    } catch (InterruptedException __omp__ie) {//####[265]####
                        __omp__ie.printStackTrace();//####[266]####
                    }//####[267]####
                }//####[268]####
            } else {//####[271]####
                for (int x = 0; x < width; x = x + 1) //####[273]####
                {//####[273]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[274]####
                    {//####[274]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[275]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[276]####
                        __omp_vars.R = (int) (Color.red(__omp_vars.pixel) * __omp_vars.red);//####[277]####
                        __omp_vars.G = (int) (Color.green(__omp_vars.pixel) * __omp_vars.green);//####[278]####
                        __omp_vars.B = (int) (Color.blue(__omp_vars.pixel) * __omp_vars.blue);//####[279]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[280]####
                    }//####[281]####
                }//####[282]####
            }//####[283]####
        }//####[285]####
        __omp_vars.bmOut = bmOut;//####[287]####
        __omp_vars.G = G;//####[288]####
        __omp_vars.height = height;//####[289]####
        __omp_vars.red = red;//####[290]####
        __omp_vars.pixel = pixel;//####[291]####
        __omp_vars.A = A;//####[292]####
        __omp_vars.blue = blue;//####[293]####
        __omp_vars.B = B;//####[294]####
        __omp_vars.green = green;//####[295]####
        __omp_vars.R = R;//####[296]####
        __omp_vars.width = width;//####[297]####
        __omp_vars.src = src;//####[298]####
    }//####[299]####
//####[299]####
//####[300]####
    public static Bitmap gaussian(Bitmap src) {//####[300]####
        {//####[300]####
            double[][] GaussianBlurConfig = new double[][] { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };//####[301]####
            ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);//####[302]####
            convMatrix.applyConfig(GaussianBlurConfig);//####[303]####
            convMatrix.Factor = 16;//####[304]####
            convMatrix.Offset = 0;//####[305]####
            return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);//####[306]####
        }//####[307]####
    }//####[308]####
//####[310]####
    public static Bitmap cdepth(Bitmap src, int bitOffset) {//####[310]####
        {//####[310]####
            int width = src.getWidth();//####[311]####
            int height = src.getHeight();//####[312]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[313]####
            int A = 0, R = 0, G = 0, B = 0;//####[314]####
            int pixel = 0;//####[315]####
            if (Pyjama.insideParallelRegion()) //####[317]####
            {//####[317]####
                {//####[319]####
                    for (int x = 0; x < width; x = x + 1) //####[320]####
                    {//####[320]####
                        for (int y = 0; y < height; ++y) //####[321]####
                        {//####[321]####
                            pixel = src.getPixel(x, y);//####[322]####
                            A = Color.alpha(pixel);//####[323]####
                            R = Color.red(pixel);//####[324]####
                            G = Color.green(pixel);//####[325]####
                            B = Color.blue(pixel);//####[326]####
                            R = ((R + (bitOffset / 2)) - ((R + (bitOffset / 2)) % bitOffset) - 1);//####[327]####
                            if (R < 0) //####[328]####
                            {//####[328]####
                                R = 0;//####[329]####
                            }//####[330]####
                            G = ((G + (bitOffset / 2)) - ((G + (bitOffset / 2)) % bitOffset) - 1);//####[331]####
                            if (G < 0) //####[332]####
                            {//####[332]####
                                G = 0;//####[333]####
                            }//####[334]####
                            B = ((B + (bitOffset / 2)) - ((B + (bitOffset / 2)) % bitOffset) - 1);//####[335]####
                            if (B < 0) //####[336]####
                            {//####[336]####
                                B = 0;//####[337]####
                            }//####[338]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[339]####
                        }//####[340]####
                    }//####[341]####
                }//####[342]####
            } else {//####[343]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[345]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing3 _omp__parallelRegionVarHolderInstance_3 = new _omp__parallelRegionVarHolderClass_BitmapProcessing3();//####[348]####
                _omp__parallelRegionVarHolderInstance_3.bmOut = bmOut;//####[349]####
                _omp__parallelRegionVarHolderInstance_3.G = G;//####[350]####
                _omp__parallelRegionVarHolderInstance_3.height = height;//####[351]####
                _omp__parallelRegionVarHolderInstance_3.pixel = pixel;//####[352]####
                _omp__parallelRegionVarHolderInstance_3.A = A;//####[353]####
                _omp__parallelRegionVarHolderInstance_3.B = B;//####[354]####
                _omp__parallelRegionVarHolderInstance_3.R = R;//####[355]####
                _omp__parallelRegionVarHolderInstance_3.width = width;//####[356]####
                _omp__parallelRegionVarHolderInstance_3.bitOffset = bitOffset;//####[357]####
                _omp__parallelRegionVarHolderInstance_3.src = src;//####[358]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[361]####
                TaskID _omp__parallelRegionTaskID_3 = _ompParallelRegion_3(_omp__parallelRegionVarHolderInstance_3);//####[362]####
                __pt___ompParallelRegion_3(_omp__parallelRegionVarHolderInstance_3);//####[363]####
                try {//####[364]####
                    _omp__parallelRegionTaskID_3.waitTillFinished();//####[364]####
                } catch (Exception __pt__ex) {//####[364]####
                    __pt__ex.printStackTrace();//####[364]####
                }//####[364]####
                PJPackageOnly.setMasterThread(null);//####[366]####
                _holderForPIFirst.set(true);//####[367]####
                bmOut = _omp__parallelRegionVarHolderInstance_3.bmOut;//####[369]####
                G = _omp__parallelRegionVarHolderInstance_3.G;//####[370]####
                height = _omp__parallelRegionVarHolderInstance_3.height;//####[371]####
                pixel = _omp__parallelRegionVarHolderInstance_3.pixel;//####[372]####
                A = _omp__parallelRegionVarHolderInstance_3.A;//####[373]####
                B = _omp__parallelRegionVarHolderInstance_3.B;//####[374]####
                R = _omp__parallelRegionVarHolderInstance_3.R;//####[375]####
                width = _omp__parallelRegionVarHolderInstance_3.width;//####[376]####
                bitOffset = _omp__parallelRegionVarHolderInstance_3.bitOffset;//####[377]####
                src = _omp__parallelRegionVarHolderInstance_3.src;//####[378]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[379]####
            }//####[380]####
            src.recycle();//####[383]####
            src = null;//####[384]####
            return bmOut;//####[385]####
        }//####[386]####
    }//####[387]####
//####[388]####
    private static AtomicBoolean _imFirst_5 = new AtomicBoolean(true);//####[388]####
//####[389]####
    private static AtomicInteger _imFinishedCounter_5 = new AtomicInteger(0);//####[389]####
//####[390]####
    private static CountDownLatch _waitBarrier_5 = new CountDownLatch(1);//####[390]####
//####[391]####
    private static CountDownLatch _waitBarrierAfter_5 = new CountDownLatch(1);//####[391]####
//####[392]####
    private static ParIterator<Integer> _pi_5 = null;//####[392]####
//####[393]####
    private static Integer _lastElement_5 = null;//####[393]####
//####[394]####
    private static _ompWorkSharedUserCode_BitmapProcessing5_variables _ompWorkSharedUserCode_BitmapProcessing5_variables_instance = null;//####[394]####
//####[395]####
    private static void _ompWorkSharedUserCode_BitmapProcessing5(_ompWorkSharedUserCode_BitmapProcessing5_variables __omp_vars) {//####[395]####
        int width = __omp_vars.width;//####[397]####
        int height = __omp_vars.height;//####[398]####
        int A = __omp_vars.A;//####[399]####
        int R = __omp_vars.R;//####[400]####
        int G = __omp_vars.G;//####[401]####
        int B = __omp_vars.B;//####[402]####
        int pixel = __omp_vars.pixel;//####[403]####
        int bitOffset = __omp_vars.bitOffset;//####[404]####
        Bitmap bmOut = __omp_vars.bmOut;//####[405]####
        Bitmap src = __omp_vars.src;//####[406]####
        Integer x;//####[407]####
        while (_pi_5.hasNext()) //####[408]####
        {//####[408]####
            x = _pi_5.next();//####[409]####
            {//####[411]####
                for (int y = 0; y < height; ++y) //####[412]####
                {//####[412]####
                    pixel = src.getPixel(x, y);//####[413]####
                    A = Color.alpha(pixel);//####[414]####
                    R = Color.red(pixel);//####[415]####
                    G = Color.green(pixel);//####[416]####
                    B = Color.blue(pixel);//####[417]####
                    R = ((R + (bitOffset / 2)) - ((R + (bitOffset / 2)) % bitOffset) - 1);//####[418]####
                    if (R < 0) //####[419]####
                    {//####[419]####
                        R = 0;//####[420]####
                    }//####[421]####
                    G = ((G + (bitOffset / 2)) - ((G + (bitOffset / 2)) % bitOffset) - 1);//####[422]####
                    if (G < 0) //####[423]####
                    {//####[423]####
                        G = 0;//####[424]####
                    }//####[425]####
                    B = ((B + (bitOffset / 2)) - ((B + (bitOffset / 2)) % bitOffset) - 1);//####[426]####
                    if (B < 0) //####[427]####
                    {//####[427]####
                        B = 0;//####[428]####
                    }//####[429]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[430]####
                }//####[431]####
            }//####[432]####
        }//####[433]####
        __omp_vars.src = src;//####[435]####
    }//####[436]####
//####[440]####
    private static volatile Method __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method = null;//####[440]####
    private synchronized static void __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_ensureMethodVarSet() {//####[440]####
        if (__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method == null) {//####[440]####
            try {//####[440]####
                __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_3", new Class[] {//####[440]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing3.class//####[440]####
                });//####[440]####
            } catch (Exception e) {//####[440]####
                e.printStackTrace();//####[440]####
            }//####[440]####
        }//####[440]####
    }//####[440]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(_omp__parallelRegionVarHolderClass_BitmapProcessing3 __omp_vars) {//####[440]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[440]####
        return _ompParallelRegion_3(__omp_vars, new TaskInfo());//####[440]####
    }//####[440]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(_omp__parallelRegionVarHolderClass_BitmapProcessing3 __omp_vars, TaskInfo taskinfo) {//####[440]####
        // ensure Method variable is set//####[440]####
        if (__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method == null) {//####[440]####
            __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_ensureMethodVarSet();//####[440]####
        }//####[440]####
        taskinfo.setParameters(__omp_vars);//####[440]####
        taskinfo.setMethod(__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method);//####[440]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[440]####
    }//####[440]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing3> __omp_vars) {//####[440]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[440]####
        return _ompParallelRegion_3(__omp_vars, new TaskInfo());//####[440]####
    }//####[440]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing3> __omp_vars, TaskInfo taskinfo) {//####[440]####
        // ensure Method variable is set//####[440]####
        if (__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method == null) {//####[440]####
            __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_ensureMethodVarSet();//####[440]####
        }//####[440]####
        taskinfo.setTaskIdArgIndexes(0);//####[440]####
        taskinfo.addDependsOn(__omp_vars);//####[440]####
        taskinfo.setParameters(__omp_vars);//####[440]####
        taskinfo.setMethod(__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method);//####[440]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[440]####
    }//####[440]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing3> __omp_vars) {//####[440]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[440]####
        return _ompParallelRegion_3(__omp_vars, new TaskInfo());//####[440]####
    }//####[440]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing3> __omp_vars, TaskInfo taskinfo) {//####[440]####
        // ensure Method variable is set//####[440]####
        if (__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method == null) {//####[440]####
            __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_ensureMethodVarSet();//####[440]####
        }//####[440]####
        taskinfo.setQueueArgIndexes(0);//####[440]####
        taskinfo.setIsPipeline(true);//####[440]####
        taskinfo.setParameters(__omp_vars);//####[440]####
        taskinfo.setMethod(__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method);//####[440]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[440]####
    }//####[440]####
    public static void __pt___ompParallelRegion_3(_omp__parallelRegionVarHolderClass_BitmapProcessing3 __omp_vars) {//####[440]####
        Bitmap bmOut = __omp_vars.bmOut;//####[442]####
        int G = __omp_vars.G;//####[443]####
        int height = __omp_vars.height;//####[444]####
        int pixel = __omp_vars.pixel;//####[445]####
        int A = __omp_vars.A;//####[446]####
        int B = __omp_vars.B;//####[447]####
        int R = __omp_vars.R;//####[448]####
        int width = __omp_vars.width;//####[449]####
        int bitOffset = __omp_vars.bitOffset;//####[450]####
        Bitmap src = __omp_vars.src;//####[451]####
        {//####[452]####
            if (Pyjama.insideParallelRegion()) //####[453]####
            {//####[453]####
                boolean _omp_imFirst = _imFirst_5.getAndSet(false);//####[455]####
                _holderForPIFirst = _imFirst_5;//####[456]####
                if (_omp_imFirst) //####[457]####
                {//####[457]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing5_variables();//####[458]####
                    int __omp_size_ = 0;//####[459]####
                    for (int x = 0; x < width; x = x + 1) //####[461]####
                    {//####[461]####
                        _lastElement_5 = x;//####[462]####
                        __omp_size_++;//####[463]####
                    }//####[464]####
                    _pi_5 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[465]####
                    _omp_piVarContainer.add(_pi_5);//####[466]####
                    _pi_5.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[467]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.width = width;//####[469]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.height = height;//####[470]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.A = A;//####[471]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.R = R;//####[472]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.G = G;//####[473]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.B = B;//####[474]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.pixel = pixel;//####[475]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.bitOffset = bitOffset;//####[476]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.bmOut = bmOut;//####[477]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.src = src;//####[478]####
                    _waitBarrier_5.countDown();//####[479]####
                } else {//####[480]####
                    try {//####[481]####
                        _waitBarrier_5.await();//####[481]####
                    } catch (InterruptedException __omp__ie) {//####[481]####
                        __omp__ie.printStackTrace();//####[481]####
                    }//####[481]####
                }//####[482]####
                _ompWorkSharedUserCode_BitmapProcessing5(_ompWorkSharedUserCode_BitmapProcessing5_variables_instance);//####[483]####
                if (_imFinishedCounter_5.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[484]####
                {//####[484]####
                    _waitBarrierAfter_5.countDown();//####[485]####
                } else {//####[486]####
                    try {//####[487]####
                        _waitBarrierAfter_5.await();//####[488]####
                    } catch (InterruptedException __omp__ie) {//####[489]####
                        __omp__ie.printStackTrace();//####[490]####
                    }//####[491]####
                }//####[492]####
            } else {//####[495]####
                for (int x = 0; x < width; x = x + 1) //####[497]####
                {//####[497]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[498]####
                    {//####[498]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[499]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[500]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[501]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[502]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[503]####
                        __omp_vars.R = ((__omp_vars.R + (__omp_vars.bitOffset / 2)) - ((__omp_vars.R + (__omp_vars.bitOffset / 2)) % __omp_vars.bitOffset) - 1);//####[504]####
                        if (__omp_vars.R < 0) //####[505]####
                        {//####[505]####
                            __omp_vars.R = 0;//####[506]####
                        }//####[507]####
                        __omp_vars.G = ((__omp_vars.G + (__omp_vars.bitOffset / 2)) - ((__omp_vars.G + (__omp_vars.bitOffset / 2)) % __omp_vars.bitOffset) - 1);//####[508]####
                        if (__omp_vars.G < 0) //####[509]####
                        {//####[509]####
                            __omp_vars.G = 0;//####[510]####
                        }//####[511]####
                        __omp_vars.B = ((__omp_vars.B + (__omp_vars.bitOffset / 2)) - ((__omp_vars.B + (__omp_vars.bitOffset / 2)) % __omp_vars.bitOffset) - 1);//####[512]####
                        if (__omp_vars.B < 0) //####[513]####
                        {//####[513]####
                            __omp_vars.B = 0;//####[514]####
                        }//####[515]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[516]####
                    }//####[517]####
                }//####[518]####
            }//####[519]####
        }//####[521]####
        __omp_vars.bmOut = bmOut;//####[523]####
        __omp_vars.G = G;//####[524]####
        __omp_vars.height = height;//####[525]####
        __omp_vars.pixel = pixel;//####[526]####
        __omp_vars.A = A;//####[527]####
        __omp_vars.B = B;//####[528]####
        __omp_vars.R = R;//####[529]####
        __omp_vars.width = width;//####[530]####
        __omp_vars.bitOffset = bitOffset;//####[531]####
        __omp_vars.src = src;//####[532]####
    }//####[533]####
//####[533]####
//####[534]####
    public static Bitmap sharpen(Bitmap src) {//####[534]####
        {//####[534]####
            double[][] SharpConfig = new double[][] { { 0, -2, 0 }, { -2, 11, -2 }, { 0, -2, 0 } };//####[535]####
            ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);//####[536]####
            convMatrix.applyConfig(SharpConfig);//####[537]####
            convMatrix.Factor = 3;//####[538]####
            return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);//####[539]####
        }//####[540]####
    }//####[541]####
//####[543]####
    public static Bitmap noise(Bitmap source) {//####[543]####
        {//####[543]####
            int COLOR_MAX = 0xFF;//####[544]####
            int width = source.getWidth();//####[545]####
            int height = source.getHeight();//####[546]####
            int[] pixels = new int[width * height];//####[547]####
            source.getPixels(pixels, 0, width, 0, 0, width, height);//####[548]####
            Random random = new Random();//####[549]####
            int index = 0;//####[550]####
            if (Pyjama.insideParallelRegion()) //####[552]####
            {//####[552]####
                {//####[554]####
                    for (int y = 0; y < height; y = y + 1) //####[555]####
                    {//####[555]####
                        for (int x = 0; x < width; ++x) //####[556]####
                        {//####[556]####
                            index = y * width + x;//####[557]####
                            int randColor = Color.rgb(random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));//####[558]####
                            pixels[index] |= randColor;//####[559]####
                        }//####[560]####
                    }//####[561]####
                }//####[562]####
            } else {//####[563]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[565]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing6 _omp__parallelRegionVarHolderInstance_6 = new _omp__parallelRegionVarHolderClass_BitmapProcessing6();//####[568]####
                _omp__parallelRegionVarHolderInstance_6.index = index;//####[569]####
                _omp__parallelRegionVarHolderInstance_6.height = height;//####[570]####
                _omp__parallelRegionVarHolderInstance_6.source = source;//####[571]####
                _omp__parallelRegionVarHolderInstance_6.COLOR_MAX = COLOR_MAX;//####[572]####
                _omp__parallelRegionVarHolderInstance_6.width = width;//####[573]####
                _omp__parallelRegionVarHolderInstance_6.random = random;//####[574]####
                _omp__parallelRegionVarHolderInstance_6.pixels = pixels;//####[575]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[578]####
                TaskID _omp__parallelRegionTaskID_6 = _ompParallelRegion_6(_omp__parallelRegionVarHolderInstance_6);//####[579]####
                __pt___ompParallelRegion_6(_omp__parallelRegionVarHolderInstance_6);//####[580]####
                try {//####[581]####
                    _omp__parallelRegionTaskID_6.waitTillFinished();//####[581]####
                } catch (Exception __pt__ex) {//####[581]####
                    __pt__ex.printStackTrace();//####[581]####
                }//####[581]####
                PJPackageOnly.setMasterThread(null);//####[583]####
                _holderForPIFirst.set(true);//####[584]####
                index = _omp__parallelRegionVarHolderInstance_6.index;//####[586]####
                height = _omp__parallelRegionVarHolderInstance_6.height;//####[587]####
                source = _omp__parallelRegionVarHolderInstance_6.source;//####[588]####
                COLOR_MAX = _omp__parallelRegionVarHolderInstance_6.COLOR_MAX;//####[589]####
                width = _omp__parallelRegionVarHolderInstance_6.width;//####[590]####
                random = _omp__parallelRegionVarHolderInstance_6.random;//####[591]####
                pixels = _omp__parallelRegionVarHolderInstance_6.pixels;//####[592]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[593]####
            }//####[594]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, source.getConfig());//####[597]####
            bmOut.setPixels(pixels, 0, width, 0, 0, width, height);//####[598]####
            source.recycle();//####[599]####
            source = null;//####[600]####
            return bmOut;//####[601]####
        }//####[602]####
    }//####[603]####
//####[604]####
    private static AtomicBoolean _imFirst_8 = new AtomicBoolean(true);//####[604]####
//####[605]####
    private static AtomicInteger _imFinishedCounter_8 = new AtomicInteger(0);//####[605]####
//####[606]####
    private static CountDownLatch _waitBarrier_8 = new CountDownLatch(1);//####[606]####
//####[607]####
    private static CountDownLatch _waitBarrierAfter_8 = new CountDownLatch(1);//####[607]####
//####[608]####
    private static ParIterator<Integer> _pi_8 = null;//####[608]####
//####[609]####
    private static Integer _lastElement_8 = null;//####[609]####
//####[610]####
    private static _ompWorkSharedUserCode_BitmapProcessing8_variables _ompWorkSharedUserCode_BitmapProcessing8_variables_instance = null;//####[610]####
//####[611]####
    private static void _ompWorkSharedUserCode_BitmapProcessing8(_ompWorkSharedUserCode_BitmapProcessing8_variables __omp_vars) {//####[611]####
        int width = __omp_vars.width;//####[613]####
        int height = __omp_vars.height;//####[614]####
        int[] pixels = __omp_vars.pixels;//####[615]####
        int COLOR_MAX = __omp_vars.COLOR_MAX;//####[616]####
        int index = __omp_vars.index;//####[617]####
        Bitmap source = __omp_vars.source;//####[618]####
        Random random = __omp_vars.random;//####[619]####
        Integer y;//####[620]####
        while (_pi_8.hasNext()) //####[621]####
        {//####[621]####
            y = _pi_8.next();//####[622]####
            {//####[624]####
                for (int x = 0; x < width; ++x) //####[625]####
                {//####[625]####
                    index = y * width + x;//####[626]####
                    int randColor = Color.rgb(random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));//####[627]####
                    pixels[index] |= randColor;//####[628]####
                }//####[629]####
            }//####[630]####
        }//####[631]####
        __omp_vars.index = index;//####[633]####
        __omp_vars.source = source;//####[634]####
        __omp_vars.random = random;//####[635]####
    }//####[636]####
//####[640]####
    private static volatile Method __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method = null;//####[640]####
    private synchronized static void __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_ensureMethodVarSet() {//####[640]####
        if (__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method == null) {//####[640]####
            try {//####[640]####
                __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_6", new Class[] {//####[640]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing6.class//####[640]####
                });//####[640]####
            } catch (Exception e) {//####[640]####
                e.printStackTrace();//####[640]####
            }//####[640]####
        }//####[640]####
    }//####[640]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(_omp__parallelRegionVarHolderClass_BitmapProcessing6 __omp_vars) {//####[640]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[640]####
        return _ompParallelRegion_6(__omp_vars, new TaskInfo());//####[640]####
    }//####[640]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(_omp__parallelRegionVarHolderClass_BitmapProcessing6 __omp_vars, TaskInfo taskinfo) {//####[640]####
        // ensure Method variable is set//####[640]####
        if (__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method == null) {//####[640]####
            __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_ensureMethodVarSet();//####[640]####
        }//####[640]####
        taskinfo.setParameters(__omp_vars);//####[640]####
        taskinfo.setMethod(__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method);//####[640]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[640]####
    }//####[640]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing6> __omp_vars) {//####[640]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[640]####
        return _ompParallelRegion_6(__omp_vars, new TaskInfo());//####[640]####
    }//####[640]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing6> __omp_vars, TaskInfo taskinfo) {//####[640]####
        // ensure Method variable is set//####[640]####
        if (__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method == null) {//####[640]####
            __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_ensureMethodVarSet();//####[640]####
        }//####[640]####
        taskinfo.setTaskIdArgIndexes(0);//####[640]####
        taskinfo.addDependsOn(__omp_vars);//####[640]####
        taskinfo.setParameters(__omp_vars);//####[640]####
        taskinfo.setMethod(__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method);//####[640]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[640]####
    }//####[640]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing6> __omp_vars) {//####[640]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[640]####
        return _ompParallelRegion_6(__omp_vars, new TaskInfo());//####[640]####
    }//####[640]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing6> __omp_vars, TaskInfo taskinfo) {//####[640]####
        // ensure Method variable is set//####[640]####
        if (__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method == null) {//####[640]####
            __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_ensureMethodVarSet();//####[640]####
        }//####[640]####
        taskinfo.setQueueArgIndexes(0);//####[640]####
        taskinfo.setIsPipeline(true);//####[640]####
        taskinfo.setParameters(__omp_vars);//####[640]####
        taskinfo.setMethod(__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method);//####[640]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[640]####
    }//####[640]####
    public static void __pt___ompParallelRegion_6(_omp__parallelRegionVarHolderClass_BitmapProcessing6 __omp_vars) {//####[640]####
        int index = __omp_vars.index;//####[642]####
        int height = __omp_vars.height;//####[643]####
        Bitmap source = __omp_vars.source;//####[644]####
        int COLOR_MAX = __omp_vars.COLOR_MAX;//####[645]####
        int width = __omp_vars.width;//####[646]####
        Random random = __omp_vars.random;//####[647]####
        int[] pixels = __omp_vars.pixels;//####[648]####
        {//####[649]####
            if (Pyjama.insideParallelRegion()) //####[650]####
            {//####[650]####
                boolean _omp_imFirst = _imFirst_8.getAndSet(false);//####[652]####
                _holderForPIFirst = _imFirst_8;//####[653]####
                if (_omp_imFirst) //####[654]####
                {//####[654]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing8_variables();//####[655]####
                    int __omp_size_ = 0;//####[656]####
                    for (int y = 0; y < height; y = y + 1) //####[658]####
                    {//####[658]####
                        _lastElement_8 = y;//####[659]####
                        __omp_size_++;//####[660]####
                    }//####[661]####
                    _pi_8 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[662]####
                    _omp_piVarContainer.add(_pi_8);//####[663]####
                    _pi_8.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[664]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.width = width;//####[666]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.height = height;//####[667]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.pixels = pixels;//####[668]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.COLOR_MAX = COLOR_MAX;//####[669]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.index = index;//####[670]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.source = source;//####[671]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.random = random;//####[672]####
                    _waitBarrier_8.countDown();//####[673]####
                } else {//####[674]####
                    try {//####[675]####
                        _waitBarrier_8.await();//####[675]####
                    } catch (InterruptedException __omp__ie) {//####[675]####
                        __omp__ie.printStackTrace();//####[675]####
                    }//####[675]####
                }//####[676]####
                _ompWorkSharedUserCode_BitmapProcessing8(_ompWorkSharedUserCode_BitmapProcessing8_variables_instance);//####[677]####
                if (_imFinishedCounter_8.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[678]####
                {//####[678]####
                    _waitBarrierAfter_8.countDown();//####[679]####
                } else {//####[680]####
                    try {//####[681]####
                        _waitBarrierAfter_8.await();//####[682]####
                    } catch (InterruptedException __omp__ie) {//####[683]####
                        __omp__ie.printStackTrace();//####[684]####
                    }//####[685]####
                }//####[686]####
            } else {//####[689]####
                for (int y = 0; y < height; y = y + 1) //####[691]####
                {//####[691]####
                    for (int x = 0; x < __omp_vars.width; ++x) //####[692]####
                    {//####[692]####
                        __omp_vars.index = y * __omp_vars.width + x;//####[693]####
                        int randColor = Color.rgb(__omp_vars.random.nextInt(__omp_vars.COLOR_MAX), __omp_vars.random.nextInt(__omp_vars.COLOR_MAX), __omp_vars.random.nextInt(__omp_vars.COLOR_MAX));//####[694]####
                        __omp_vars.pixels[__omp_vars.index] |= randColor;//####[695]####
                    }//####[696]####
                }//####[697]####
            }//####[698]####
        }//####[700]####
        __omp_vars.index = index;//####[702]####
        __omp_vars.height = height;//####[703]####
        __omp_vars.source = source;//####[704]####
        __omp_vars.COLOR_MAX = COLOR_MAX;//####[705]####
        __omp_vars.width = width;//####[706]####
        __omp_vars.random = random;//####[707]####
        __omp_vars.pixels = pixels;//####[708]####
    }//####[709]####
//####[709]####
//####[710]####
    public static Bitmap brightness(Bitmap src, int value) {//####[710]####
        {//####[710]####
            int width = src.getWidth();//####[711]####
            int height = src.getHeight();//####[712]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[713]####
            int A = 0, R = 0, G = 0, B = 0;//####[714]####
            int pixel = 0;//####[715]####
            if (Pyjama.insideParallelRegion()) //####[717]####
            {//####[717]####
                {//####[719]####
                    for (int x = 0; x < width; x = x + 1) //####[720]####
                    {//####[720]####
                        for (int y = 0; y < height; ++y) //####[721]####
                        {//####[721]####
                            pixel = src.getPixel(x, y);//####[722]####
                            A = Color.alpha(pixel);//####[723]####
                            R = Color.red(pixel);//####[724]####
                            G = Color.green(pixel);//####[725]####
                            B = Color.blue(pixel);//####[726]####
                            R += value;//####[727]####
                            if (R > 255) //####[728]####
                            {//####[728]####
                                R = 255;//####[729]####
                            } else if (R < 0) //####[730]####
                            {//####[730]####
                                R = 0;//####[731]####
                            }//####[732]####
                            G += value;//####[733]####
                            if (G > 255) //####[734]####
                            {//####[734]####
                                G = 255;//####[735]####
                            } else if (G < 0) //####[736]####
                            {//####[736]####
                                G = 0;//####[737]####
                            }//####[738]####
                            B += value;//####[739]####
                            if (B > 255) //####[740]####
                            {//####[740]####
                                B = 255;//####[741]####
                            } else if (B < 0) //####[742]####
                            {//####[742]####
                                B = 0;//####[743]####
                            }//####[744]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[745]####
                        }//####[746]####
                    }//####[747]####
                }//####[748]####
            } else {//####[749]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[751]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing9 _omp__parallelRegionVarHolderInstance_9 = new _omp__parallelRegionVarHolderClass_BitmapProcessing9();//####[754]####
                _omp__parallelRegionVarHolderInstance_9.bmOut = bmOut;//####[755]####
                _omp__parallelRegionVarHolderInstance_9.G = G;//####[756]####
                _omp__parallelRegionVarHolderInstance_9.height = height;//####[757]####
                _omp__parallelRegionVarHolderInstance_9.pixel = pixel;//####[758]####
                _omp__parallelRegionVarHolderInstance_9.A = A;//####[759]####
                _omp__parallelRegionVarHolderInstance_9.B = B;//####[760]####
                _omp__parallelRegionVarHolderInstance_9.R = R;//####[761]####
                _omp__parallelRegionVarHolderInstance_9.width = width;//####[762]####
                _omp__parallelRegionVarHolderInstance_9.value = value;//####[763]####
                _omp__parallelRegionVarHolderInstance_9.src = src;//####[764]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[767]####
                TaskID _omp__parallelRegionTaskID_9 = _ompParallelRegion_9(_omp__parallelRegionVarHolderInstance_9);//####[768]####
                __pt___ompParallelRegion_9(_omp__parallelRegionVarHolderInstance_9);//####[769]####
                try {//####[770]####
                    _omp__parallelRegionTaskID_9.waitTillFinished();//####[770]####
                } catch (Exception __pt__ex) {//####[770]####
                    __pt__ex.printStackTrace();//####[770]####
                }//####[770]####
                PJPackageOnly.setMasterThread(null);//####[772]####
                _holderForPIFirst.set(true);//####[773]####
                bmOut = _omp__parallelRegionVarHolderInstance_9.bmOut;//####[775]####
                G = _omp__parallelRegionVarHolderInstance_9.G;//####[776]####
                height = _omp__parallelRegionVarHolderInstance_9.height;//####[777]####
                pixel = _omp__parallelRegionVarHolderInstance_9.pixel;//####[778]####
                A = _omp__parallelRegionVarHolderInstance_9.A;//####[779]####
                B = _omp__parallelRegionVarHolderInstance_9.B;//####[780]####
                R = _omp__parallelRegionVarHolderInstance_9.R;//####[781]####
                width = _omp__parallelRegionVarHolderInstance_9.width;//####[782]####
                value = _omp__parallelRegionVarHolderInstance_9.value;//####[783]####
                src = _omp__parallelRegionVarHolderInstance_9.src;//####[784]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[785]####
            }//####[786]####
            src.recycle();//####[789]####
            src = null;//####[790]####
            return bmOut;//####[791]####
        }//####[792]####
    }//####[793]####
//####[794]####
    private static AtomicBoolean _imFirst_11 = new AtomicBoolean(true);//####[794]####
//####[795]####
    private static AtomicInteger _imFinishedCounter_11 = new AtomicInteger(0);//####[795]####
//####[796]####
    private static CountDownLatch _waitBarrier_11 = new CountDownLatch(1);//####[796]####
//####[797]####
    private static CountDownLatch _waitBarrierAfter_11 = new CountDownLatch(1);//####[797]####
//####[798]####
    private static ParIterator<Integer> _pi_11 = null;//####[798]####
//####[799]####
    private static Integer _lastElement_11 = null;//####[799]####
//####[800]####
    private static _ompWorkSharedUserCode_BitmapProcessing11_variables _ompWorkSharedUserCode_BitmapProcessing11_variables_instance = null;//####[800]####
//####[801]####
    private static void _ompWorkSharedUserCode_BitmapProcessing11(_ompWorkSharedUserCode_BitmapProcessing11_variables __omp_vars) {//####[801]####
        int width = __omp_vars.width;//####[803]####
        int height = __omp_vars.height;//####[804]####
        int A = __omp_vars.A;//####[805]####
        int R = __omp_vars.R;//####[806]####
        int G = __omp_vars.G;//####[807]####
        int B = __omp_vars.B;//####[808]####
        int pixel = __omp_vars.pixel;//####[809]####
        Bitmap bmOut = __omp_vars.bmOut;//####[810]####
        int value = __omp_vars.value;//####[811]####
        Bitmap src = __omp_vars.src;//####[812]####
        Integer x;//####[813]####
        while (_pi_11.hasNext()) //####[814]####
        {//####[814]####
            x = _pi_11.next();//####[815]####
            {//####[817]####
                for (int y = 0; y < height; ++y) //####[818]####
                {//####[818]####
                    pixel = src.getPixel(x, y);//####[819]####
                    A = Color.alpha(pixel);//####[820]####
                    R = Color.red(pixel);//####[821]####
                    G = Color.green(pixel);//####[822]####
                    B = Color.blue(pixel);//####[823]####
                    R += value;//####[824]####
                    if (R > 255) //####[825]####
                    {//####[825]####
                        R = 255;//####[826]####
                    } else if (R < 0) //####[827]####
                    {//####[827]####
                        R = 0;//####[828]####
                    }//####[829]####
                    G += value;//####[830]####
                    if (G > 255) //####[831]####
                    {//####[831]####
                        G = 255;//####[832]####
                    } else if (G < 0) //####[833]####
                    {//####[833]####
                        G = 0;//####[834]####
                    }//####[835]####
                    B += value;//####[836]####
                    if (B > 255) //####[837]####
                    {//####[837]####
                        B = 255;//####[838]####
                    } else if (B < 0) //####[839]####
                    {//####[839]####
                        B = 0;//####[840]####
                    }//####[841]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[842]####
                }//####[843]####
            }//####[844]####
        }//####[845]####
        __omp_vars.value = value;//####[847]####
        __omp_vars.src = src;//####[848]####
    }//####[849]####
//####[853]####
    private static volatile Method __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method = null;//####[853]####
    private synchronized static void __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_ensureMethodVarSet() {//####[853]####
        if (__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method == null) {//####[853]####
            try {//####[853]####
                __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_9", new Class[] {//####[853]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing9.class//####[853]####
                });//####[853]####
            } catch (Exception e) {//####[853]####
                e.printStackTrace();//####[853]####
            }//####[853]####
        }//####[853]####
    }//####[853]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(_omp__parallelRegionVarHolderClass_BitmapProcessing9 __omp_vars) {//####[853]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[853]####
        return _ompParallelRegion_9(__omp_vars, new TaskInfo());//####[853]####
    }//####[853]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(_omp__parallelRegionVarHolderClass_BitmapProcessing9 __omp_vars, TaskInfo taskinfo) {//####[853]####
        // ensure Method variable is set//####[853]####
        if (__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method == null) {//####[853]####
            __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_ensureMethodVarSet();//####[853]####
        }//####[853]####
        taskinfo.setParameters(__omp_vars);//####[853]####
        taskinfo.setMethod(__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method);//####[853]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[853]####
    }//####[853]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing9> __omp_vars) {//####[853]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[853]####
        return _ompParallelRegion_9(__omp_vars, new TaskInfo());//####[853]####
    }//####[853]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing9> __omp_vars, TaskInfo taskinfo) {//####[853]####
        // ensure Method variable is set//####[853]####
        if (__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method == null) {//####[853]####
            __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_ensureMethodVarSet();//####[853]####
        }//####[853]####
        taskinfo.setTaskIdArgIndexes(0);//####[853]####
        taskinfo.addDependsOn(__omp_vars);//####[853]####
        taskinfo.setParameters(__omp_vars);//####[853]####
        taskinfo.setMethod(__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method);//####[853]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[853]####
    }//####[853]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing9> __omp_vars) {//####[853]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[853]####
        return _ompParallelRegion_9(__omp_vars, new TaskInfo());//####[853]####
    }//####[853]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing9> __omp_vars, TaskInfo taskinfo) {//####[853]####
        // ensure Method variable is set//####[853]####
        if (__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method == null) {//####[853]####
            __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_ensureMethodVarSet();//####[853]####
        }//####[853]####
        taskinfo.setQueueArgIndexes(0);//####[853]####
        taskinfo.setIsPipeline(true);//####[853]####
        taskinfo.setParameters(__omp_vars);//####[853]####
        taskinfo.setMethod(__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method);//####[853]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[853]####
    }//####[853]####
    public static void __pt___ompParallelRegion_9(_omp__parallelRegionVarHolderClass_BitmapProcessing9 __omp_vars) {//####[853]####
        Bitmap bmOut = __omp_vars.bmOut;//####[855]####
        int G = __omp_vars.G;//####[856]####
        int height = __omp_vars.height;//####[857]####
        int pixel = __omp_vars.pixel;//####[858]####
        int A = __omp_vars.A;//####[859]####
        int B = __omp_vars.B;//####[860]####
        int R = __omp_vars.R;//####[861]####
        int width = __omp_vars.width;//####[862]####
        int value = __omp_vars.value;//####[863]####
        Bitmap src = __omp_vars.src;//####[864]####
        {//####[865]####
            if (Pyjama.insideParallelRegion()) //####[866]####
            {//####[866]####
                boolean _omp_imFirst = _imFirst_11.getAndSet(false);//####[868]####
                _holderForPIFirst = _imFirst_11;//####[869]####
                if (_omp_imFirst) //####[870]####
                {//####[870]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing11_variables();//####[871]####
                    int __omp_size_ = 0;//####[872]####
                    for (int x = 0; x < width; x = x + 1) //####[874]####
                    {//####[874]####
                        _lastElement_11 = x;//####[875]####
                        __omp_size_++;//####[876]####
                    }//####[877]####
                    _pi_11 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[878]####
                    _omp_piVarContainer.add(_pi_11);//####[879]####
                    _pi_11.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[880]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.width = width;//####[882]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.height = height;//####[883]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.A = A;//####[884]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.R = R;//####[885]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.G = G;//####[886]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.B = B;//####[887]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.pixel = pixel;//####[888]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.bmOut = bmOut;//####[889]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.value = value;//####[890]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.src = src;//####[891]####
                    _waitBarrier_11.countDown();//####[892]####
                } else {//####[893]####
                    try {//####[894]####
                        _waitBarrier_11.await();//####[894]####
                    } catch (InterruptedException __omp__ie) {//####[894]####
                        __omp__ie.printStackTrace();//####[894]####
                    }//####[894]####
                }//####[895]####
                _ompWorkSharedUserCode_BitmapProcessing11(_ompWorkSharedUserCode_BitmapProcessing11_variables_instance);//####[896]####
                if (_imFinishedCounter_11.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[897]####
                {//####[897]####
                    _waitBarrierAfter_11.countDown();//####[898]####
                } else {//####[899]####
                    try {//####[900]####
                        _waitBarrierAfter_11.await();//####[901]####
                    } catch (InterruptedException __omp__ie) {//####[902]####
                        __omp__ie.printStackTrace();//####[903]####
                    }//####[904]####
                }//####[905]####
            } else {//####[908]####
                for (int x = 0; x < width; x = x + 1) //####[910]####
                {//####[910]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[911]####
                    {//####[911]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[912]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[913]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[914]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[915]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[916]####
                        __omp_vars.R += __omp_vars.value;//####[917]####
                        if (__omp_vars.R > 255) //####[918]####
                        {//####[918]####
                            __omp_vars.R = 255;//####[919]####
                        } else if (__omp_vars.R < 0) //####[920]####
                        {//####[920]####
                            __omp_vars.R = 0;//####[921]####
                        }//####[922]####
                        __omp_vars.G += __omp_vars.value;//####[923]####
                        if (__omp_vars.G > 255) //####[924]####
                        {//####[924]####
                            __omp_vars.G = 255;//####[925]####
                        } else if (__omp_vars.G < 0) //####[926]####
                        {//####[926]####
                            __omp_vars.G = 0;//####[927]####
                        }//####[928]####
                        __omp_vars.B += __omp_vars.value;//####[929]####
                        if (__omp_vars.B > 255) //####[930]####
                        {//####[930]####
                            __omp_vars.B = 255;//####[931]####
                        } else if (__omp_vars.B < 0) //####[932]####
                        {//####[932]####
                            __omp_vars.B = 0;//####[933]####
                        }//####[934]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[935]####
                    }//####[936]####
                }//####[937]####
            }//####[938]####
        }//####[940]####
        __omp_vars.bmOut = bmOut;//####[942]####
        __omp_vars.G = G;//####[943]####
        __omp_vars.height = height;//####[944]####
        __omp_vars.pixel = pixel;//####[945]####
        __omp_vars.A = A;//####[946]####
        __omp_vars.B = B;//####[947]####
        __omp_vars.R = R;//####[948]####
        __omp_vars.width = width;//####[949]####
        __omp_vars.value = value;//####[950]####
        __omp_vars.src = src;//####[951]####
    }//####[952]####
//####[952]####
//####[953]####
    public static Bitmap sepia(Bitmap src) {//####[953]####
        {//####[953]####
            int width = src.getWidth();//####[954]####
            int height = src.getHeight();//####[955]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[956]####
            double GS_RED = 0.3;//####[957]####
            double GS_GREEN = 0.59;//####[958]####
            double GS_BLUE = 0.11;//####[959]####
            int A = 0, R = 0, G = 0, B = 0;//####[960]####
            int pixel = 0;//####[961]####
            if (Pyjama.insideParallelRegion()) //####[963]####
            {//####[963]####
                {//####[965]####
                    for (int x = 0; x < width; x = x + 1) //####[966]####
                    {//####[966]####
                        for (int y = 0; y < height; ++y) //####[967]####
                        {//####[967]####
                            pixel = src.getPixel(x, y);//####[968]####
                            A = Color.alpha(pixel);//####[969]####
                            R = Color.red(pixel);//####[970]####
                            G = Color.green(pixel);//####[971]####
                            B = Color.blue(pixel);//####[972]####
                            B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);//####[973]####
                            R += 110;//####[974]####
                            if (R > 255) //####[975]####
                            {//####[975]####
                                R = 255;//####[976]####
                            }//####[977]####
                            G += 65;//####[978]####
                            if (G > 255) //####[979]####
                            {//####[979]####
                                G = 255;//####[980]####
                            }//####[981]####
                            B += 20;//####[982]####
                            if (B > 255) //####[983]####
                            {//####[983]####
                                B = 255;//####[984]####
                            }//####[985]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[986]####
                        }//####[987]####
                    }//####[988]####
                }//####[989]####
            } else {//####[990]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[992]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing12 _omp__parallelRegionVarHolderInstance_12 = new _omp__parallelRegionVarHolderClass_BitmapProcessing12();//####[995]####
                _omp__parallelRegionVarHolderInstance_12.GS_GREEN = GS_GREEN;//####[996]####
                _omp__parallelRegionVarHolderInstance_12.bmOut = bmOut;//####[997]####
                _omp__parallelRegionVarHolderInstance_12.G = G;//####[998]####
                _omp__parallelRegionVarHolderInstance_12.height = height;//####[999]####
                _omp__parallelRegionVarHolderInstance_12.pixel = pixel;//####[1000]####
                _omp__parallelRegionVarHolderInstance_12.A = A;//####[1001]####
                _omp__parallelRegionVarHolderInstance_12.B = B;//####[1002]####
                _omp__parallelRegionVarHolderInstance_12.GS_BLUE = GS_BLUE;//####[1003]####
                _omp__parallelRegionVarHolderInstance_12.R = R;//####[1004]####
                _omp__parallelRegionVarHolderInstance_12.width = width;//####[1005]####
                _omp__parallelRegionVarHolderInstance_12.src = src;//####[1006]####
                _omp__parallelRegionVarHolderInstance_12.GS_RED = GS_RED;//####[1007]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[1010]####
                TaskID _omp__parallelRegionTaskID_12 = _ompParallelRegion_12(_omp__parallelRegionVarHolderInstance_12);//####[1011]####
                __pt___ompParallelRegion_12(_omp__parallelRegionVarHolderInstance_12);//####[1012]####
                try {//####[1013]####
                    _omp__parallelRegionTaskID_12.waitTillFinished();//####[1013]####
                } catch (Exception __pt__ex) {//####[1013]####
                    __pt__ex.printStackTrace();//####[1013]####
                }//####[1013]####
                PJPackageOnly.setMasterThread(null);//####[1015]####
                _holderForPIFirst.set(true);//####[1016]####
                GS_GREEN = _omp__parallelRegionVarHolderInstance_12.GS_GREEN;//####[1018]####
                bmOut = _omp__parallelRegionVarHolderInstance_12.bmOut;//####[1019]####
                G = _omp__parallelRegionVarHolderInstance_12.G;//####[1020]####
                height = _omp__parallelRegionVarHolderInstance_12.height;//####[1021]####
                pixel = _omp__parallelRegionVarHolderInstance_12.pixel;//####[1022]####
                A = _omp__parallelRegionVarHolderInstance_12.A;//####[1023]####
                B = _omp__parallelRegionVarHolderInstance_12.B;//####[1024]####
                GS_BLUE = _omp__parallelRegionVarHolderInstance_12.GS_BLUE;//####[1025]####
                R = _omp__parallelRegionVarHolderInstance_12.R;//####[1026]####
                width = _omp__parallelRegionVarHolderInstance_12.width;//####[1027]####
                src = _omp__parallelRegionVarHolderInstance_12.src;//####[1028]####
                GS_RED = _omp__parallelRegionVarHolderInstance_12.GS_RED;//####[1029]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[1030]####
            }//####[1031]####
            src.recycle();//####[1034]####
            src = null;//####[1035]####
            return bmOut;//####[1036]####
        }//####[1037]####
    }//####[1038]####
//####[1039]####
    private static AtomicBoolean _imFirst_14 = new AtomicBoolean(true);//####[1039]####
//####[1040]####
    private static AtomicInteger _imFinishedCounter_14 = new AtomicInteger(0);//####[1040]####
//####[1041]####
    private static CountDownLatch _waitBarrier_14 = new CountDownLatch(1);//####[1041]####
//####[1042]####
    private static CountDownLatch _waitBarrierAfter_14 = new CountDownLatch(1);//####[1042]####
//####[1043]####
    private static ParIterator<Integer> _pi_14 = null;//####[1043]####
//####[1044]####
    private static Integer _lastElement_14 = null;//####[1044]####
//####[1045]####
    private static _ompWorkSharedUserCode_BitmapProcessing14_variables _ompWorkSharedUserCode_BitmapProcessing14_variables_instance = null;//####[1045]####
//####[1046]####
    private static void _ompWorkSharedUserCode_BitmapProcessing14(_ompWorkSharedUserCode_BitmapProcessing14_variables __omp_vars) {//####[1046]####
        int width = __omp_vars.width;//####[1048]####
        int height = __omp_vars.height;//####[1049]####
        int A = __omp_vars.A;//####[1050]####
        int R = __omp_vars.R;//####[1051]####
        int G = __omp_vars.G;//####[1052]####
        int B = __omp_vars.B;//####[1053]####
        int pixel = __omp_vars.pixel;//####[1054]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1055]####
        double GS_GREEN = __omp_vars.GS_GREEN;//####[1056]####
        double GS_BLUE = __omp_vars.GS_BLUE;//####[1057]####
        Bitmap src = __omp_vars.src;//####[1058]####
        double GS_RED = __omp_vars.GS_RED;//####[1059]####
        Integer x;//####[1060]####
        while (_pi_14.hasNext()) //####[1061]####
        {//####[1061]####
            x = _pi_14.next();//####[1062]####
            {//####[1064]####
                for (int y = 0; y < height; ++y) //####[1065]####
                {//####[1065]####
                    pixel = src.getPixel(x, y);//####[1066]####
                    A = Color.alpha(pixel);//####[1067]####
                    R = Color.red(pixel);//####[1068]####
                    G = Color.green(pixel);//####[1069]####
                    B = Color.blue(pixel);//####[1070]####
                    B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);//####[1071]####
                    R += 110;//####[1072]####
                    if (R > 255) //####[1073]####
                    {//####[1073]####
                        R = 255;//####[1074]####
                    }//####[1075]####
                    G += 65;//####[1076]####
                    if (G > 255) //####[1077]####
                    {//####[1077]####
                        G = 255;//####[1078]####
                    }//####[1079]####
                    B += 20;//####[1080]####
                    if (B > 255) //####[1081]####
                    {//####[1081]####
                        B = 255;//####[1082]####
                    }//####[1083]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1084]####
                }//####[1085]####
            }//####[1086]####
        }//####[1087]####
        __omp_vars.GS_GREEN = GS_GREEN;//####[1089]####
        __omp_vars.GS_BLUE = GS_BLUE;//####[1090]####
        __omp_vars.src = src;//####[1091]####
        __omp_vars.GS_RED = GS_RED;//####[1092]####
    }//####[1093]####
//####[1097]####
    private static volatile Method __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method = null;//####[1097]####
    private synchronized static void __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_ensureMethodVarSet() {//####[1097]####
        if (__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method == null) {//####[1097]####
            try {//####[1097]####
                __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_12", new Class[] {//####[1097]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing12.class//####[1097]####
                });//####[1097]####
            } catch (Exception e) {//####[1097]####
                e.printStackTrace();//####[1097]####
            }//####[1097]####
        }//####[1097]####
    }//####[1097]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(_omp__parallelRegionVarHolderClass_BitmapProcessing12 __omp_vars) {//####[1097]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1097]####
        return _ompParallelRegion_12(__omp_vars, new TaskInfo());//####[1097]####
    }//####[1097]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(_omp__parallelRegionVarHolderClass_BitmapProcessing12 __omp_vars, TaskInfo taskinfo) {//####[1097]####
        // ensure Method variable is set//####[1097]####
        if (__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method == null) {//####[1097]####
            __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_ensureMethodVarSet();//####[1097]####
        }//####[1097]####
        taskinfo.setParameters(__omp_vars);//####[1097]####
        taskinfo.setMethod(__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method);//####[1097]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1097]####
    }//####[1097]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing12> __omp_vars) {//####[1097]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1097]####
        return _ompParallelRegion_12(__omp_vars, new TaskInfo());//####[1097]####
    }//####[1097]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing12> __omp_vars, TaskInfo taskinfo) {//####[1097]####
        // ensure Method variable is set//####[1097]####
        if (__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method == null) {//####[1097]####
            __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_ensureMethodVarSet();//####[1097]####
        }//####[1097]####
        taskinfo.setTaskIdArgIndexes(0);//####[1097]####
        taskinfo.addDependsOn(__omp_vars);//####[1097]####
        taskinfo.setParameters(__omp_vars);//####[1097]####
        taskinfo.setMethod(__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method);//####[1097]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1097]####
    }//####[1097]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing12> __omp_vars) {//####[1097]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1097]####
        return _ompParallelRegion_12(__omp_vars, new TaskInfo());//####[1097]####
    }//####[1097]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing12> __omp_vars, TaskInfo taskinfo) {//####[1097]####
        // ensure Method variable is set//####[1097]####
        if (__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method == null) {//####[1097]####
            __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_ensureMethodVarSet();//####[1097]####
        }//####[1097]####
        taskinfo.setQueueArgIndexes(0);//####[1097]####
        taskinfo.setIsPipeline(true);//####[1097]####
        taskinfo.setParameters(__omp_vars);//####[1097]####
        taskinfo.setMethod(__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method);//####[1097]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1097]####
    }//####[1097]####
    public static void __pt___ompParallelRegion_12(_omp__parallelRegionVarHolderClass_BitmapProcessing12 __omp_vars) {//####[1097]####
        double GS_GREEN = __omp_vars.GS_GREEN;//####[1099]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1100]####
        int G = __omp_vars.G;//####[1101]####
        int height = __omp_vars.height;//####[1102]####
        int pixel = __omp_vars.pixel;//####[1103]####
        int A = __omp_vars.A;//####[1104]####
        int B = __omp_vars.B;//####[1105]####
        double GS_BLUE = __omp_vars.GS_BLUE;//####[1106]####
        int R = __omp_vars.R;//####[1107]####
        int width = __omp_vars.width;//####[1108]####
        Bitmap src = __omp_vars.src;//####[1109]####
        double GS_RED = __omp_vars.GS_RED;//####[1110]####
        {//####[1111]####
            if (Pyjama.insideParallelRegion()) //####[1112]####
            {//####[1112]####
                boolean _omp_imFirst = _imFirst_14.getAndSet(false);//####[1114]####
                _holderForPIFirst = _imFirst_14;//####[1115]####
                if (_omp_imFirst) //####[1116]####
                {//####[1116]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing14_variables();//####[1117]####
                    int __omp_size_ = 0;//####[1118]####
                    for (int x = 0; x < width; x = x + 1) //####[1120]####
                    {//####[1120]####
                        _lastElement_14 = x;//####[1121]####
                        __omp_size_++;//####[1122]####
                    }//####[1123]####
                    _pi_14 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[1124]####
                    _omp_piVarContainer.add(_pi_14);//####[1125]####
                    _pi_14.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[1126]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.width = width;//####[1128]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.height = height;//####[1129]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.A = A;//####[1130]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.R = R;//####[1131]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.G = G;//####[1132]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.B = B;//####[1133]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.pixel = pixel;//####[1134]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.bmOut = bmOut;//####[1135]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.GS_GREEN = GS_GREEN;//####[1136]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.GS_BLUE = GS_BLUE;//####[1137]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.src = src;//####[1138]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.GS_RED = GS_RED;//####[1139]####
                    _waitBarrier_14.countDown();//####[1140]####
                } else {//####[1141]####
                    try {//####[1142]####
                        _waitBarrier_14.await();//####[1142]####
                    } catch (InterruptedException __omp__ie) {//####[1142]####
                        __omp__ie.printStackTrace();//####[1142]####
                    }//####[1142]####
                }//####[1143]####
                _ompWorkSharedUserCode_BitmapProcessing14(_ompWorkSharedUserCode_BitmapProcessing14_variables_instance);//####[1144]####
                if (_imFinishedCounter_14.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[1145]####
                {//####[1145]####
                    _waitBarrierAfter_14.countDown();//####[1146]####
                } else {//####[1147]####
                    try {//####[1148]####
                        _waitBarrierAfter_14.await();//####[1149]####
                    } catch (InterruptedException __omp__ie) {//####[1150]####
                        __omp__ie.printStackTrace();//####[1151]####
                    }//####[1152]####
                }//####[1153]####
            } else {//####[1156]####
                for (int x = 0; x < width; x = x + 1) //####[1158]####
                {//####[1158]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[1159]####
                    {//####[1159]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[1160]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[1161]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[1162]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[1163]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[1164]####
                        __omp_vars.B = __omp_vars.G = __omp_vars.R = (int) (__omp_vars.GS_RED * __omp_vars.R + __omp_vars.GS_GREEN * __omp_vars.G + __omp_vars.GS_BLUE * __omp_vars.B);//####[1165]####
                        __omp_vars.R += 110;//####[1166]####
                        if (__omp_vars.R > 255) //####[1167]####
                        {//####[1167]####
                            __omp_vars.R = 255;//####[1168]####
                        }//####[1169]####
                        __omp_vars.G += 65;//####[1170]####
                        if (__omp_vars.G > 255) //####[1171]####
                        {//####[1171]####
                            __omp_vars.G = 255;//####[1172]####
                        }//####[1173]####
                        __omp_vars.B += 20;//####[1174]####
                        if (__omp_vars.B > 255) //####[1175]####
                        {//####[1175]####
                            __omp_vars.B = 255;//####[1176]####
                        }//####[1177]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[1178]####
                    }//####[1179]####
                }//####[1180]####
            }//####[1181]####
        }//####[1183]####
        __omp_vars.GS_GREEN = GS_GREEN;//####[1185]####
        __omp_vars.bmOut = bmOut;//####[1186]####
        __omp_vars.G = G;//####[1187]####
        __omp_vars.height = height;//####[1188]####
        __omp_vars.pixel = pixel;//####[1189]####
        __omp_vars.A = A;//####[1190]####
        __omp_vars.B = B;//####[1191]####
        __omp_vars.GS_BLUE = GS_BLUE;//####[1192]####
        __omp_vars.R = R;//####[1193]####
        __omp_vars.width = width;//####[1194]####
        __omp_vars.src = src;//####[1195]####
        __omp_vars.GS_RED = GS_RED;//####[1196]####
    }//####[1197]####
//####[1197]####
//####[1198]####
    public static Bitmap gamma(Bitmap src, double red, double green, double blue) {//####[1198]####
        {//####[1198]####
            red = (double) (red + 2) / 10.0;//####[1199]####
            green = (double) (green + 2) / 10.0;//####[1200]####
            blue = (double) (blue + 2) / 10.0;//####[1201]####
            Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());//####[1202]####
            int width = src.getWidth();//####[1203]####
            int height = src.getHeight();//####[1204]####
            int A = 0, R = 0, G = 0, B = 0;//####[1205]####
            int pixel = 0;//####[1206]####
            int MAX_SIZE = 256;//####[1207]####
            double MAX_VALUE_DBL = 255.0;//####[1208]####
            int MAX_VALUE_INT = 255;//####[1209]####
            double REVERSE = 1.0;//####[1210]####
            int[] gammaR = new int[MAX_SIZE];//####[1211]####
            int[] gammaG = new int[MAX_SIZE];//####[1212]####
            int[] gammaB = new int[MAX_SIZE];//####[1213]####
            if (Pyjama.insideParallelRegion()) //####[1215]####
            {//####[1215]####
                {//####[1217]####
                    for (int i = 0; i < MAX_SIZE; i = i + 1) //####[1218]####
                    {//####[1218]####
                        gammaR[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));//####[1219]####
                        gammaG[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));//####[1220]####
                        gammaB[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));//####[1221]####
                    }//####[1222]####
                }//####[1223]####
            } else {//####[1224]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[1226]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing15 _omp__parallelRegionVarHolderInstance_15 = new _omp__parallelRegionVarHolderClass_BitmapProcessing15();//####[1229]####
                _omp__parallelRegionVarHolderInstance_15.MAX_VALUE_INT = MAX_VALUE_INT;//####[1230]####
                _omp__parallelRegionVarHolderInstance_15.G = G;//####[1231]####
                _omp__parallelRegionVarHolderInstance_15.pixel = pixel;//####[1232]####
                _omp__parallelRegionVarHolderInstance_15.A = A;//####[1233]####
                _omp__parallelRegionVarHolderInstance_15.green = green;//####[1234]####
                _omp__parallelRegionVarHolderInstance_15.MAX_SIZE = MAX_SIZE;//####[1235]####
                _omp__parallelRegionVarHolderInstance_15.B = B;//####[1236]####
                _omp__parallelRegionVarHolderInstance_15.width = width;//####[1237]####
                _omp__parallelRegionVarHolderInstance_15.gammaB = gammaB;//####[1238]####
                _omp__parallelRegionVarHolderInstance_15.bmOut = bmOut;//####[1239]####
                _omp__parallelRegionVarHolderInstance_15.red = red;//####[1240]####
                _omp__parallelRegionVarHolderInstance_15.height = height;//####[1241]####
                _omp__parallelRegionVarHolderInstance_15.blue = blue;//####[1242]####
                _omp__parallelRegionVarHolderInstance_15.gammaR = gammaR;//####[1243]####
                _omp__parallelRegionVarHolderInstance_15.R = R;//####[1244]####
                _omp__parallelRegionVarHolderInstance_15.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1245]####
                _omp__parallelRegionVarHolderInstance_15.REVERSE = REVERSE;//####[1246]####
                _omp__parallelRegionVarHolderInstance_15.src = src;//####[1247]####
                _omp__parallelRegionVarHolderInstance_15.gammaG = gammaG;//####[1248]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[1251]####
                TaskID _omp__parallelRegionTaskID_15 = _ompParallelRegion_15(_omp__parallelRegionVarHolderInstance_15);//####[1252]####
                __pt___ompParallelRegion_15(_omp__parallelRegionVarHolderInstance_15);//####[1253]####
                try {//####[1254]####
                    _omp__parallelRegionTaskID_15.waitTillFinished();//####[1254]####
                } catch (Exception __pt__ex) {//####[1254]####
                    __pt__ex.printStackTrace();//####[1254]####
                }//####[1254]####
                PJPackageOnly.setMasterThread(null);//####[1256]####
                _holderForPIFirst.set(true);//####[1257]####
                MAX_VALUE_INT = _omp__parallelRegionVarHolderInstance_15.MAX_VALUE_INT;//####[1259]####
                G = _omp__parallelRegionVarHolderInstance_15.G;//####[1260]####
                pixel = _omp__parallelRegionVarHolderInstance_15.pixel;//####[1261]####
                A = _omp__parallelRegionVarHolderInstance_15.A;//####[1262]####
                green = _omp__parallelRegionVarHolderInstance_15.green;//####[1263]####
                MAX_SIZE = _omp__parallelRegionVarHolderInstance_15.MAX_SIZE;//####[1264]####
                B = _omp__parallelRegionVarHolderInstance_15.B;//####[1265]####
                width = _omp__parallelRegionVarHolderInstance_15.width;//####[1266]####
                gammaB = _omp__parallelRegionVarHolderInstance_15.gammaB;//####[1267]####
                bmOut = _omp__parallelRegionVarHolderInstance_15.bmOut;//####[1268]####
                red = _omp__parallelRegionVarHolderInstance_15.red;//####[1269]####
                height = _omp__parallelRegionVarHolderInstance_15.height;//####[1270]####
                blue = _omp__parallelRegionVarHolderInstance_15.blue;//####[1271]####
                gammaR = _omp__parallelRegionVarHolderInstance_15.gammaR;//####[1272]####
                R = _omp__parallelRegionVarHolderInstance_15.R;//####[1273]####
                MAX_VALUE_DBL = _omp__parallelRegionVarHolderInstance_15.MAX_VALUE_DBL;//####[1274]####
                REVERSE = _omp__parallelRegionVarHolderInstance_15.REVERSE;//####[1275]####
                src = _omp__parallelRegionVarHolderInstance_15.src;//####[1276]####
                gammaG = _omp__parallelRegionVarHolderInstance_15.gammaG;//####[1277]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[1278]####
            }//####[1279]####
            if (Pyjama.insideParallelRegion()) //####[1283]####
            {//####[1283]####
                {//####[1285]####
                    for (int x = 0; x < width; x = x + 1) //####[1286]####
                    {//####[1286]####
                        for (int y = 0; y < height; y++) //####[1287]####
                        {//####[1287]####
                            pixel = src.getPixel(x, y);//####[1288]####
                            A = Color.alpha(pixel);//####[1289]####
                            R = gammaR[Color.red(pixel)];//####[1290]####
                            G = gammaG[Color.green(pixel)];//####[1291]####
                            B = gammaB[Color.blue(pixel)];//####[1292]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1293]####
                        }//####[1294]####
                    }//####[1295]####
                }//####[1296]####
            } else {//####[1297]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[1299]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing18 _omp__parallelRegionVarHolderInstance_18 = new _omp__parallelRegionVarHolderClass_BitmapProcessing18();//####[1302]####
                _omp__parallelRegionVarHolderInstance_18.G = G;//####[1303]####
                _omp__parallelRegionVarHolderInstance_18.MAX_VALUE_INT = MAX_VALUE_INT;//####[1304]####
                _omp__parallelRegionVarHolderInstance_18.A = A;//####[1305]####
                _omp__parallelRegionVarHolderInstance_18.pixel = pixel;//####[1306]####
                _omp__parallelRegionVarHolderInstance_18.B = B;//####[1307]####
                _omp__parallelRegionVarHolderInstance_18.MAX_SIZE = MAX_SIZE;//####[1308]####
                _omp__parallelRegionVarHolderInstance_18.green = green;//####[1309]####
                _omp__parallelRegionVarHolderInstance_18.width = width;//####[1310]####
                _omp__parallelRegionVarHolderInstance_18.gammaB = gammaB;//####[1311]####
                _omp__parallelRegionVarHolderInstance_18.bmOut = bmOut;//####[1312]####
                _omp__parallelRegionVarHolderInstance_18.height = height;//####[1313]####
                _omp__parallelRegionVarHolderInstance_18.red = red;//####[1314]####
                _omp__parallelRegionVarHolderInstance_18.blue = blue;//####[1315]####
                _omp__parallelRegionVarHolderInstance_18.gammaR = gammaR;//####[1316]####
                _omp__parallelRegionVarHolderInstance_18.R = R;//####[1317]####
                _omp__parallelRegionVarHolderInstance_18.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1318]####
                _omp__parallelRegionVarHolderInstance_18.REVERSE = REVERSE;//####[1319]####
                _omp__parallelRegionVarHolderInstance_18.src = src;//####[1320]####
                _omp__parallelRegionVarHolderInstance_18.gammaG = gammaG;//####[1321]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[1324]####
                TaskID _omp__parallelRegionTaskID_18 = _ompParallelRegion_18(_omp__parallelRegionVarHolderInstance_18);//####[1325]####
                __pt___ompParallelRegion_18(_omp__parallelRegionVarHolderInstance_18);//####[1326]####
                try {//####[1327]####
                    _omp__parallelRegionTaskID_18.waitTillFinished();//####[1327]####
                } catch (Exception __pt__ex) {//####[1327]####
                    __pt__ex.printStackTrace();//####[1327]####
                }//####[1327]####
                PJPackageOnly.setMasterThread(null);//####[1329]####
                _holderForPIFirst.set(true);//####[1330]####
                G = _omp__parallelRegionVarHolderInstance_18.G;//####[1332]####
                MAX_VALUE_INT = _omp__parallelRegionVarHolderInstance_18.MAX_VALUE_INT;//####[1333]####
                A = _omp__parallelRegionVarHolderInstance_18.A;//####[1334]####
                pixel = _omp__parallelRegionVarHolderInstance_18.pixel;//####[1335]####
                B = _omp__parallelRegionVarHolderInstance_18.B;//####[1336]####
                MAX_SIZE = _omp__parallelRegionVarHolderInstance_18.MAX_SIZE;//####[1337]####
                green = _omp__parallelRegionVarHolderInstance_18.green;//####[1338]####
                width = _omp__parallelRegionVarHolderInstance_18.width;//####[1339]####
                gammaB = _omp__parallelRegionVarHolderInstance_18.gammaB;//####[1340]####
                bmOut = _omp__parallelRegionVarHolderInstance_18.bmOut;//####[1341]####
                height = _omp__parallelRegionVarHolderInstance_18.height;//####[1342]####
                red = _omp__parallelRegionVarHolderInstance_18.red;//####[1343]####
                blue = _omp__parallelRegionVarHolderInstance_18.blue;//####[1344]####
                gammaR = _omp__parallelRegionVarHolderInstance_18.gammaR;//####[1345]####
                R = _omp__parallelRegionVarHolderInstance_18.R;//####[1346]####
                MAX_VALUE_DBL = _omp__parallelRegionVarHolderInstance_18.MAX_VALUE_DBL;//####[1347]####
                REVERSE = _omp__parallelRegionVarHolderInstance_18.REVERSE;//####[1348]####
                src = _omp__parallelRegionVarHolderInstance_18.src;//####[1349]####
                gammaG = _omp__parallelRegionVarHolderInstance_18.gammaG;//####[1350]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[1351]####
            }//####[1352]####
            src.recycle();//####[1355]####
            src = null;//####[1356]####
            return bmOut;//####[1357]####
        }//####[1358]####
    }//####[1359]####
//####[1360]####
    private static AtomicBoolean _imFirst_17 = new AtomicBoolean(true);//####[1360]####
//####[1361]####
    private static AtomicInteger _imFinishedCounter_17 = new AtomicInteger(0);//####[1361]####
//####[1362]####
    private static CountDownLatch _waitBarrier_17 = new CountDownLatch(1);//####[1362]####
//####[1363]####
    private static CountDownLatch _waitBarrierAfter_17 = new CountDownLatch(1);//####[1363]####
//####[1364]####
    private static ParIterator<Integer> _pi_17 = null;//####[1364]####
//####[1365]####
    private static Integer _lastElement_17 = null;//####[1365]####
//####[1366]####
    private static _ompWorkSharedUserCode_BitmapProcessing17_variables _ompWorkSharedUserCode_BitmapProcessing17_variables_instance = null;//####[1366]####
//####[1367]####
    private static void _ompWorkSharedUserCode_BitmapProcessing17(_ompWorkSharedUserCode_BitmapProcessing17_variables __omp_vars) {//####[1367]####
        int MAX_SIZE = __omp_vars.MAX_SIZE;//####[1369]####
        int[] gammaR = __omp_vars.gammaR;//####[1370]####
        int[] gammaG = __omp_vars.gammaG;//####[1371]####
        int[] gammaB = __omp_vars.gammaB;//####[1372]####
        int MAX_VALUE_INT = __omp_vars.MAX_VALUE_INT;//####[1373]####
        double MAX_VALUE_DBL = __omp_vars.MAX_VALUE_DBL;//####[1374]####
        double REVERSE = __omp_vars.REVERSE;//####[1375]####
        double red = __omp_vars.red;//####[1376]####
        double green = __omp_vars.green;//####[1377]####
        double blue = __omp_vars.blue;//####[1378]####
        int G = __omp_vars.G;//####[1379]####
        int A = __omp_vars.A;//####[1380]####
        int pixel = __omp_vars.pixel;//####[1381]####
        int B = __omp_vars.B;//####[1382]####
        int width = __omp_vars.width;//####[1383]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1384]####
        int height = __omp_vars.height;//####[1385]####
        int R = __omp_vars.R;//####[1386]####
        Bitmap src = __omp_vars.src;//####[1387]####
        Integer i;//####[1388]####
        while (_pi_17.hasNext()) //####[1389]####
        {//####[1389]####
            i = _pi_17.next();//####[1390]####
            {//####[1392]####
                gammaR[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));//####[1393]####
                gammaG[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));//####[1394]####
                gammaB[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));//####[1395]####
            }//####[1396]####
        }//####[1397]####
        __omp_vars.G = G;//####[1399]####
        __omp_vars.A = A;//####[1400]####
        __omp_vars.pixel = pixel;//####[1401]####
        __omp_vars.B = B;//####[1402]####
        __omp_vars.width = width;//####[1403]####
        __omp_vars.bmOut = bmOut;//####[1404]####
        __omp_vars.height = height;//####[1405]####
        __omp_vars.R = R;//####[1406]####
        __omp_vars.src = src;//####[1407]####
    }//####[1408]####
//####[1412]####
    private static volatile Method __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method = null;//####[1412]####
    private synchronized static void __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_ensureMethodVarSet() {//####[1412]####
        if (__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method == null) {//####[1412]####
            try {//####[1412]####
                __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_15", new Class[] {//####[1412]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing15.class//####[1412]####
                });//####[1412]####
            } catch (Exception e) {//####[1412]####
                e.printStackTrace();//####[1412]####
            }//####[1412]####
        }//####[1412]####
    }//####[1412]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(_omp__parallelRegionVarHolderClass_BitmapProcessing15 __omp_vars) {//####[1412]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1412]####
        return _ompParallelRegion_15(__omp_vars, new TaskInfo());//####[1412]####
    }//####[1412]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(_omp__parallelRegionVarHolderClass_BitmapProcessing15 __omp_vars, TaskInfo taskinfo) {//####[1412]####
        // ensure Method variable is set//####[1412]####
        if (__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method == null) {//####[1412]####
            __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_ensureMethodVarSet();//####[1412]####
        }//####[1412]####
        taskinfo.setParameters(__omp_vars);//####[1412]####
        taskinfo.setMethod(__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method);//####[1412]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1412]####
    }//####[1412]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing15> __omp_vars) {//####[1412]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1412]####
        return _ompParallelRegion_15(__omp_vars, new TaskInfo());//####[1412]####
    }//####[1412]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing15> __omp_vars, TaskInfo taskinfo) {//####[1412]####
        // ensure Method variable is set//####[1412]####
        if (__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method == null) {//####[1412]####
            __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_ensureMethodVarSet();//####[1412]####
        }//####[1412]####
        taskinfo.setTaskIdArgIndexes(0);//####[1412]####
        taskinfo.addDependsOn(__omp_vars);//####[1412]####
        taskinfo.setParameters(__omp_vars);//####[1412]####
        taskinfo.setMethod(__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method);//####[1412]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1412]####
    }//####[1412]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing15> __omp_vars) {//####[1412]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1412]####
        return _ompParallelRegion_15(__omp_vars, new TaskInfo());//####[1412]####
    }//####[1412]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing15> __omp_vars, TaskInfo taskinfo) {//####[1412]####
        // ensure Method variable is set//####[1412]####
        if (__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method == null) {//####[1412]####
            __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_ensureMethodVarSet();//####[1412]####
        }//####[1412]####
        taskinfo.setQueueArgIndexes(0);//####[1412]####
        taskinfo.setIsPipeline(true);//####[1412]####
        taskinfo.setParameters(__omp_vars);//####[1412]####
        taskinfo.setMethod(__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method);//####[1412]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1412]####
    }//####[1412]####
    public static void __pt___ompParallelRegion_15(_omp__parallelRegionVarHolderClass_BitmapProcessing15 __omp_vars) {//####[1412]####
        int MAX_VALUE_INT = __omp_vars.MAX_VALUE_INT;//####[1414]####
        int G = __omp_vars.G;//####[1415]####
        int pixel = __omp_vars.pixel;//####[1416]####
        int A = __omp_vars.A;//####[1417]####
        double green = __omp_vars.green;//####[1418]####
        int MAX_SIZE = __omp_vars.MAX_SIZE;//####[1419]####
        int B = __omp_vars.B;//####[1420]####
        int width = __omp_vars.width;//####[1421]####
        int[] gammaB = __omp_vars.gammaB;//####[1422]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1423]####
        double red = __omp_vars.red;//####[1424]####
        int height = __omp_vars.height;//####[1425]####
        double blue = __omp_vars.blue;//####[1426]####
        int[] gammaR = __omp_vars.gammaR;//####[1427]####
        int R = __omp_vars.R;//####[1428]####
        double MAX_VALUE_DBL = __omp_vars.MAX_VALUE_DBL;//####[1429]####
        double REVERSE = __omp_vars.REVERSE;//####[1430]####
        Bitmap src = __omp_vars.src;//####[1431]####
        int[] gammaG = __omp_vars.gammaG;//####[1432]####
        {//####[1433]####
            if (Pyjama.insideParallelRegion()) //####[1434]####
            {//####[1434]####
                boolean _omp_imFirst = _imFirst_17.getAndSet(false);//####[1436]####
                _holderForPIFirst = _imFirst_17;//####[1437]####
                if (_omp_imFirst) //####[1438]####
                {//####[1438]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing17_variables();//####[1439]####
                    int __omp_size_ = 0;//####[1440]####
                    for (int i = 0; i < MAX_SIZE; i = i + 1) //####[1442]####
                    {//####[1442]####
                        _lastElement_17 = i;//####[1443]####
                        __omp_size_++;//####[1444]####
                    }//####[1445]####
                    _pi_17 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[1446]####
                    _omp_piVarContainer.add(_pi_17);//####[1447]####
                    _pi_17.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[1448]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.MAX_SIZE = MAX_SIZE;//####[1450]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.gammaR = gammaR;//####[1451]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.gammaG = gammaG;//####[1452]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.gammaB = gammaB;//####[1453]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.MAX_VALUE_INT = MAX_VALUE_INT;//####[1454]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1455]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.REVERSE = REVERSE;//####[1456]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.red = red;//####[1457]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.green = green;//####[1458]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.blue = blue;//####[1459]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.G = G;//####[1460]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.A = A;//####[1461]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.pixel = pixel;//####[1462]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.B = B;//####[1463]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.width = width;//####[1464]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.bmOut = bmOut;//####[1465]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.height = height;//####[1466]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.R = R;//####[1467]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.src = src;//####[1468]####
                    _waitBarrier_17.countDown();//####[1469]####
                } else {//####[1470]####
                    try {//####[1471]####
                        _waitBarrier_17.await();//####[1471]####
                    } catch (InterruptedException __omp__ie) {//####[1471]####
                        __omp__ie.printStackTrace();//####[1471]####
                    }//####[1471]####
                }//####[1472]####
                _ompWorkSharedUserCode_BitmapProcessing17(_ompWorkSharedUserCode_BitmapProcessing17_variables_instance);//####[1473]####
                if (_imFinishedCounter_17.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[1474]####
                {//####[1474]####
                    _waitBarrierAfter_17.countDown();//####[1475]####
                } else {//####[1476]####
                    try {//####[1477]####
                        _waitBarrierAfter_17.await();//####[1478]####
                    } catch (InterruptedException __omp__ie) {//####[1479]####
                        __omp__ie.printStackTrace();//####[1480]####
                    }//####[1481]####
                }//####[1482]####
            } else {//####[1485]####
                for (int i = 0; i < MAX_SIZE; i = i + 1) //####[1487]####
                {//####[1487]####
                    __omp_vars.gammaR[i] = (int) Math.min(__omp_vars.MAX_VALUE_INT, (int) ((__omp_vars.MAX_VALUE_DBL * Math.pow(i / __omp_vars.MAX_VALUE_DBL, __omp_vars.REVERSE / __omp_vars.red)) + 0.5));//####[1488]####
                    __omp_vars.gammaG[i] = (int) Math.min(__omp_vars.MAX_VALUE_INT, (int) ((__omp_vars.MAX_VALUE_DBL * Math.pow(i / __omp_vars.MAX_VALUE_DBL, __omp_vars.REVERSE / __omp_vars.green)) + 0.5));//####[1489]####
                    __omp_vars.gammaB[i] = (int) Math.min(__omp_vars.MAX_VALUE_INT, (int) ((__omp_vars.MAX_VALUE_DBL * Math.pow(i / __omp_vars.MAX_VALUE_DBL, __omp_vars.REVERSE / __omp_vars.blue)) + 0.5));//####[1490]####
                }//####[1491]####
            }//####[1492]####
        }//####[1494]####
        __omp_vars.MAX_VALUE_INT = MAX_VALUE_INT;//####[1496]####
        __omp_vars.G = G;//####[1497]####
        __omp_vars.pixel = pixel;//####[1498]####
        __omp_vars.A = A;//####[1499]####
        __omp_vars.green = green;//####[1500]####
        __omp_vars.MAX_SIZE = MAX_SIZE;//####[1501]####
        __omp_vars.B = B;//####[1502]####
        __omp_vars.width = width;//####[1503]####
        __omp_vars.gammaB = gammaB;//####[1504]####
        __omp_vars.bmOut = bmOut;//####[1505]####
        __omp_vars.red = red;//####[1506]####
        __omp_vars.height = height;//####[1507]####
        __omp_vars.blue = blue;//####[1508]####
        __omp_vars.gammaR = gammaR;//####[1509]####
        __omp_vars.R = R;//####[1510]####
        __omp_vars.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1511]####
        __omp_vars.REVERSE = REVERSE;//####[1512]####
        __omp_vars.src = src;//####[1513]####
        __omp_vars.gammaG = gammaG;//####[1514]####
    }//####[1515]####
//####[1515]####
//####[1515]####
    private static AtomicBoolean _imFirst_20 = new AtomicBoolean(true);//####[1515]####
//####[1516]####
    private static AtomicInteger _imFinishedCounter_20 = new AtomicInteger(0);//####[1516]####
//####[1517]####
    private static CountDownLatch _waitBarrier_20 = new CountDownLatch(1);//####[1517]####
//####[1518]####
    private static CountDownLatch _waitBarrierAfter_20 = new CountDownLatch(1);//####[1518]####
//####[1519]####
    private static ParIterator<Integer> _pi_20 = null;//####[1519]####
//####[1520]####
    private static Integer _lastElement_20 = null;//####[1520]####
//####[1521]####
    private static _ompWorkSharedUserCode_BitmapProcessing20_variables _ompWorkSharedUserCode_BitmapProcessing20_variables_instance = null;//####[1521]####
//####[1522]####
    private static void _ompWorkSharedUserCode_BitmapProcessing20(_ompWorkSharedUserCode_BitmapProcessing20_variables __omp_vars) {//####[1522]####
        int width = __omp_vars.width;//####[1524]####
        int height = __omp_vars.height;//####[1525]####
        int A = __omp_vars.A;//####[1526]####
        int R = __omp_vars.R;//####[1527]####
        int G = __omp_vars.G;//####[1528]####
        int B = __omp_vars.B;//####[1529]####
        int pixel = __omp_vars.pixel;//####[1530]####
        int[] gammaR = __omp_vars.gammaR;//####[1531]####
        int[] gammaG = __omp_vars.gammaG;//####[1532]####
        int[] gammaB = __omp_vars.gammaB;//####[1533]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1534]####
        int MAX_VALUE_INT = __omp_vars.MAX_VALUE_INT;//####[1535]####
        int MAX_SIZE = __omp_vars.MAX_SIZE;//####[1536]####
        double green = __omp_vars.green;//####[1537]####
        double red = __omp_vars.red;//####[1538]####
        double blue = __omp_vars.blue;//####[1539]####
        double MAX_VALUE_DBL = __omp_vars.MAX_VALUE_DBL;//####[1540]####
        double REVERSE = __omp_vars.REVERSE;//####[1541]####
        Bitmap src = __omp_vars.src;//####[1542]####
        Integer x;//####[1543]####
        while (_pi_20.hasNext()) //####[1544]####
        {//####[1544]####
            x = _pi_20.next();//####[1545]####
            {//####[1547]####
                for (int y = 0; y < height; y++) //####[1548]####
                {//####[1548]####
                    pixel = src.getPixel(x, y);//####[1549]####
                    A = Color.alpha(pixel);//####[1550]####
                    R = gammaR[Color.red(pixel)];//####[1551]####
                    G = gammaG[Color.green(pixel)];//####[1552]####
                    B = gammaB[Color.blue(pixel)];//####[1553]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1554]####
                }//####[1555]####
            }//####[1556]####
        }//####[1557]####
        __omp_vars.MAX_VALUE_INT = MAX_VALUE_INT;//####[1559]####
        __omp_vars.MAX_SIZE = MAX_SIZE;//####[1560]####
        __omp_vars.green = green;//####[1561]####
        __omp_vars.red = red;//####[1562]####
        __omp_vars.blue = blue;//####[1563]####
        __omp_vars.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1564]####
        __omp_vars.REVERSE = REVERSE;//####[1565]####
        __omp_vars.src = src;//####[1566]####
    }//####[1567]####
//####[1571]####
    private static volatile Method __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method = null;//####[1571]####
    private synchronized static void __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_ensureMethodVarSet() {//####[1571]####
        if (__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method == null) {//####[1571]####
            try {//####[1571]####
                __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_18", new Class[] {//####[1571]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing18.class//####[1571]####
                });//####[1571]####
            } catch (Exception e) {//####[1571]####
                e.printStackTrace();//####[1571]####
            }//####[1571]####
        }//####[1571]####
    }//####[1571]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(_omp__parallelRegionVarHolderClass_BitmapProcessing18 __omp_vars) {//####[1571]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1571]####
        return _ompParallelRegion_18(__omp_vars, new TaskInfo());//####[1571]####
    }//####[1571]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(_omp__parallelRegionVarHolderClass_BitmapProcessing18 __omp_vars, TaskInfo taskinfo) {//####[1571]####
        // ensure Method variable is set//####[1571]####
        if (__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method == null) {//####[1571]####
            __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_ensureMethodVarSet();//####[1571]####
        }//####[1571]####
        taskinfo.setParameters(__omp_vars);//####[1571]####
        taskinfo.setMethod(__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method);//####[1571]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1571]####
    }//####[1571]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing18> __omp_vars) {//####[1571]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1571]####
        return _ompParallelRegion_18(__omp_vars, new TaskInfo());//####[1571]####
    }//####[1571]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing18> __omp_vars, TaskInfo taskinfo) {//####[1571]####
        // ensure Method variable is set//####[1571]####
        if (__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method == null) {//####[1571]####
            __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_ensureMethodVarSet();//####[1571]####
        }//####[1571]####
        taskinfo.setTaskIdArgIndexes(0);//####[1571]####
        taskinfo.addDependsOn(__omp_vars);//####[1571]####
        taskinfo.setParameters(__omp_vars);//####[1571]####
        taskinfo.setMethod(__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method);//####[1571]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1571]####
    }//####[1571]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing18> __omp_vars) {//####[1571]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1571]####
        return _ompParallelRegion_18(__omp_vars, new TaskInfo());//####[1571]####
    }//####[1571]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing18> __omp_vars, TaskInfo taskinfo) {//####[1571]####
        // ensure Method variable is set//####[1571]####
        if (__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method == null) {//####[1571]####
            __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_ensureMethodVarSet();//####[1571]####
        }//####[1571]####
        taskinfo.setQueueArgIndexes(0);//####[1571]####
        taskinfo.setIsPipeline(true);//####[1571]####
        taskinfo.setParameters(__omp_vars);//####[1571]####
        taskinfo.setMethod(__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method);//####[1571]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1571]####
    }//####[1571]####
    public static void __pt___ompParallelRegion_18(_omp__parallelRegionVarHolderClass_BitmapProcessing18 __omp_vars) {//####[1571]####
        int G = __omp_vars.G;//####[1573]####
        int MAX_VALUE_INT = __omp_vars.MAX_VALUE_INT;//####[1574]####
        int A = __omp_vars.A;//####[1575]####
        int pixel = __omp_vars.pixel;//####[1576]####
        int B = __omp_vars.B;//####[1577]####
        int MAX_SIZE = __omp_vars.MAX_SIZE;//####[1578]####
        double green = __omp_vars.green;//####[1579]####
        int width = __omp_vars.width;//####[1580]####
        int[] gammaB = __omp_vars.gammaB;//####[1581]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1582]####
        int height = __omp_vars.height;//####[1583]####
        double red = __omp_vars.red;//####[1584]####
        double blue = __omp_vars.blue;//####[1585]####
        int[] gammaR = __omp_vars.gammaR;//####[1586]####
        int R = __omp_vars.R;//####[1587]####
        double MAX_VALUE_DBL = __omp_vars.MAX_VALUE_DBL;//####[1588]####
        double REVERSE = __omp_vars.REVERSE;//####[1589]####
        Bitmap src = __omp_vars.src;//####[1590]####
        int[] gammaG = __omp_vars.gammaG;//####[1591]####
        {//####[1592]####
            if (Pyjama.insideParallelRegion()) //####[1593]####
            {//####[1593]####
                boolean _omp_imFirst = _imFirst_20.getAndSet(false);//####[1595]####
                _holderForPIFirst = _imFirst_20;//####[1596]####
                if (_omp_imFirst) //####[1597]####
                {//####[1597]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing20_variables();//####[1598]####
                    int __omp_size_ = 0;//####[1599]####
                    for (int x = 0; x < width; x = x + 1) //####[1601]####
                    {//####[1601]####
                        _lastElement_20 = x;//####[1602]####
                        __omp_size_++;//####[1603]####
                    }//####[1604]####
                    _pi_20 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[1605]####
                    _omp_piVarContainer.add(_pi_20);//####[1606]####
                    _pi_20.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[1607]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.width = width;//####[1609]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.height = height;//####[1610]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.A = A;//####[1611]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.R = R;//####[1612]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.G = G;//####[1613]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.B = B;//####[1614]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.pixel = pixel;//####[1615]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.gammaR = gammaR;//####[1616]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.gammaG = gammaG;//####[1617]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.gammaB = gammaB;//####[1618]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.bmOut = bmOut;//####[1619]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.MAX_VALUE_INT = MAX_VALUE_INT;//####[1620]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.MAX_SIZE = MAX_SIZE;//####[1621]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.green = green;//####[1622]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.red = red;//####[1623]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.blue = blue;//####[1624]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1625]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.REVERSE = REVERSE;//####[1626]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.src = src;//####[1627]####
                    _waitBarrier_20.countDown();//####[1628]####
                } else {//####[1629]####
                    try {//####[1630]####
                        _waitBarrier_20.await();//####[1630]####
                    } catch (InterruptedException __omp__ie) {//####[1630]####
                        __omp__ie.printStackTrace();//####[1630]####
                    }//####[1630]####
                }//####[1631]####
                _ompWorkSharedUserCode_BitmapProcessing20(_ompWorkSharedUserCode_BitmapProcessing20_variables_instance);//####[1632]####
                if (_imFinishedCounter_20.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[1633]####
                {//####[1633]####
                    _waitBarrierAfter_20.countDown();//####[1634]####
                } else {//####[1635]####
                    try {//####[1636]####
                        _waitBarrierAfter_20.await();//####[1637]####
                    } catch (InterruptedException __omp__ie) {//####[1638]####
                        __omp__ie.printStackTrace();//####[1639]####
                    }//####[1640]####
                }//####[1641]####
            } else {//####[1644]####
                for (int x = 0; x < width; x = x + 1) //####[1646]####
                {//####[1646]####
                    for (int y = 0; y < __omp_vars.height; y++) //####[1647]####
                    {//####[1647]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[1648]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[1649]####
                        __omp_vars.R = __omp_vars.gammaR[Color.red(__omp_vars.pixel)];//####[1650]####
                        __omp_vars.G = __omp_vars.gammaG[Color.green(__omp_vars.pixel)];//####[1651]####
                        __omp_vars.B = __omp_vars.gammaB[Color.blue(__omp_vars.pixel)];//####[1652]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[1653]####
                    }//####[1654]####
                }//####[1655]####
            }//####[1656]####
        }//####[1658]####
        __omp_vars.G = G;//####[1660]####
        __omp_vars.MAX_VALUE_INT = MAX_VALUE_INT;//####[1661]####
        __omp_vars.A = A;//####[1662]####
        __omp_vars.pixel = pixel;//####[1663]####
        __omp_vars.B = B;//####[1664]####
        __omp_vars.MAX_SIZE = MAX_SIZE;//####[1665]####
        __omp_vars.green = green;//####[1666]####
        __omp_vars.width = width;//####[1667]####
        __omp_vars.gammaB = gammaB;//####[1668]####
        __omp_vars.bmOut = bmOut;//####[1669]####
        __omp_vars.height = height;//####[1670]####
        __omp_vars.red = red;//####[1671]####
        __omp_vars.blue = blue;//####[1672]####
        __omp_vars.gammaR = gammaR;//####[1673]####
        __omp_vars.R = R;//####[1674]####
        __omp_vars.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1675]####
        __omp_vars.REVERSE = REVERSE;//####[1676]####
        __omp_vars.src = src;//####[1677]####
        __omp_vars.gammaG = gammaG;//####[1678]####
    }//####[1679]####
//####[1679]####
//####[1680]####
    public static Bitmap contrast(Bitmap src, double value) {//####[1680]####
        {//####[1680]####
            int width = src.getWidth();//####[1681]####
            int height = src.getHeight();//####[1682]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[1683]####
            Canvas c = new Canvas();//####[1684]####
            c.setBitmap(bmOut);//####[1685]####
            c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));//####[1686]####
            int A = 0, R = 0, G = 0, B = 0;//####[1687]####
            int pixel = 0;//####[1688]####
            double contrast = Math.pow((100 + value) / 100, 2);//####[1689]####
            if (Pyjama.insideParallelRegion()) //####[1691]####
            {//####[1691]####
                {//####[1693]####
                    for (int x = 0; x < width; x = x + 1) //####[1694]####
                    {//####[1694]####
                        for (int y = 0; y < height; ++y) //####[1695]####
                        {//####[1695]####
                            pixel = src.getPixel(x, y);//####[1696]####
                            A = Color.alpha(pixel);//####[1697]####
                            R = Color.red(pixel);//####[1698]####
                            R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1699]####
                            if (R < 0) //####[1700]####
                            {//####[1700]####
                                R = 0;//####[1701]####
                            } else if (R > 255) //####[1702]####
                            {//####[1702]####
                                R = 255;//####[1703]####
                            }//####[1704]####
                            G = Color.green(pixel);//####[1705]####
                            G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1706]####
                            if (G < 0) //####[1707]####
                            {//####[1707]####
                                G = 0;//####[1708]####
                            } else if (G > 255) //####[1709]####
                            {//####[1709]####
                                G = 255;//####[1710]####
                            }//####[1711]####
                            B = Color.blue(pixel);//####[1712]####
                            B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1713]####
                            if (B < 0) //####[1714]####
                            {//####[1714]####
                                B = 0;//####[1715]####
                            } else if (B > 255) //####[1716]####
                            {//####[1716]####
                                B = 255;//####[1717]####
                            }//####[1718]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1719]####
                        }//####[1720]####
                    }//####[1721]####
                }//####[1722]####
            } else {//####[1723]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[1725]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing21 _omp__parallelRegionVarHolderInstance_21 = new _omp__parallelRegionVarHolderClass_BitmapProcessing21();//####[1728]####
                _omp__parallelRegionVarHolderInstance_21.G = G;//####[1729]####
                _omp__parallelRegionVarHolderInstance_21.pixel = pixel;//####[1730]####
                _omp__parallelRegionVarHolderInstance_21.A = A;//####[1731]####
                _omp__parallelRegionVarHolderInstance_21.c = c;//####[1732]####
                _omp__parallelRegionVarHolderInstance_21.B = B;//####[1733]####
                _omp__parallelRegionVarHolderInstance_21.width = width;//####[1734]####
                _omp__parallelRegionVarHolderInstance_21.contrast = contrast;//####[1735]####
                _omp__parallelRegionVarHolderInstance_21.bmOut = bmOut;//####[1736]####
                _omp__parallelRegionVarHolderInstance_21.height = height;//####[1737]####
                _omp__parallelRegionVarHolderInstance_21.R = R;//####[1738]####
                _omp__parallelRegionVarHolderInstance_21.value = value;//####[1739]####
                _omp__parallelRegionVarHolderInstance_21.src = src;//####[1740]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[1743]####
                TaskID _omp__parallelRegionTaskID_21 = _ompParallelRegion_21(_omp__parallelRegionVarHolderInstance_21);//####[1744]####
                __pt___ompParallelRegion_21(_omp__parallelRegionVarHolderInstance_21);//####[1745]####
                try {//####[1746]####
                    _omp__parallelRegionTaskID_21.waitTillFinished();//####[1746]####
                } catch (Exception __pt__ex) {//####[1746]####
                    __pt__ex.printStackTrace();//####[1746]####
                }//####[1746]####
                PJPackageOnly.setMasterThread(null);//####[1748]####
                _holderForPIFirst.set(true);//####[1749]####
                G = _omp__parallelRegionVarHolderInstance_21.G;//####[1751]####
                pixel = _omp__parallelRegionVarHolderInstance_21.pixel;//####[1752]####
                A = _omp__parallelRegionVarHolderInstance_21.A;//####[1753]####
                c = _omp__parallelRegionVarHolderInstance_21.c;//####[1754]####
                B = _omp__parallelRegionVarHolderInstance_21.B;//####[1755]####
                width = _omp__parallelRegionVarHolderInstance_21.width;//####[1756]####
                contrast = _omp__parallelRegionVarHolderInstance_21.contrast;//####[1757]####
                bmOut = _omp__parallelRegionVarHolderInstance_21.bmOut;//####[1758]####
                height = _omp__parallelRegionVarHolderInstance_21.height;//####[1759]####
                R = _omp__parallelRegionVarHolderInstance_21.R;//####[1760]####
                value = _omp__parallelRegionVarHolderInstance_21.value;//####[1761]####
                src = _omp__parallelRegionVarHolderInstance_21.src;//####[1762]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[1763]####
            }//####[1764]####
            src.recycle();//####[1767]####
            src = null;//####[1768]####
            return bmOut;//####[1769]####
        }//####[1770]####
    }//####[1771]####
//####[1772]####
    private static AtomicBoolean _imFirst_23 = new AtomicBoolean(true);//####[1772]####
//####[1773]####
    private static AtomicInteger _imFinishedCounter_23 = new AtomicInteger(0);//####[1773]####
//####[1774]####
    private static CountDownLatch _waitBarrier_23 = new CountDownLatch(1);//####[1774]####
//####[1775]####
    private static CountDownLatch _waitBarrierAfter_23 = new CountDownLatch(1);//####[1775]####
//####[1776]####
    private static ParIterator<Integer> _pi_23 = null;//####[1776]####
//####[1777]####
    private static Integer _lastElement_23 = null;//####[1777]####
//####[1778]####
    private static _ompWorkSharedUserCode_BitmapProcessing23_variables _ompWorkSharedUserCode_BitmapProcessing23_variables_instance = null;//####[1778]####
//####[1779]####
    private static void _ompWorkSharedUserCode_BitmapProcessing23(_ompWorkSharedUserCode_BitmapProcessing23_variables __omp_vars) {//####[1779]####
        int width = __omp_vars.width;//####[1781]####
        int height = __omp_vars.height;//####[1782]####
        int A = __omp_vars.A;//####[1783]####
        int R = __omp_vars.R;//####[1784]####
        int G = __omp_vars.G;//####[1785]####
        int B = __omp_vars.B;//####[1786]####
        int pixel = __omp_vars.pixel;//####[1787]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1788]####
        Canvas c = __omp_vars.c;//####[1789]####
        double contrast = __omp_vars.contrast;//####[1790]####
        double value = __omp_vars.value;//####[1791]####
        Bitmap src = __omp_vars.src;//####[1792]####
        Integer x;//####[1793]####
        while (_pi_23.hasNext()) //####[1794]####
        {//####[1794]####
            x = _pi_23.next();//####[1795]####
            {//####[1797]####
                for (int y = 0; y < height; ++y) //####[1798]####
                {//####[1798]####
                    pixel = src.getPixel(x, y);//####[1799]####
                    A = Color.alpha(pixel);//####[1800]####
                    R = Color.red(pixel);//####[1801]####
                    R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1802]####
                    if (R < 0) //####[1803]####
                    {//####[1803]####
                        R = 0;//####[1804]####
                    } else if (R > 255) //####[1805]####
                    {//####[1805]####
                        R = 255;//####[1806]####
                    }//####[1807]####
                    G = Color.green(pixel);//####[1808]####
                    G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1809]####
                    if (G < 0) //####[1810]####
                    {//####[1810]####
                        G = 0;//####[1811]####
                    } else if (G > 255) //####[1812]####
                    {//####[1812]####
                        G = 255;//####[1813]####
                    }//####[1814]####
                    B = Color.blue(pixel);//####[1815]####
                    B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1816]####
                    if (B < 0) //####[1817]####
                    {//####[1817]####
                        B = 0;//####[1818]####
                    } else if (B > 255) //####[1819]####
                    {//####[1819]####
                        B = 255;//####[1820]####
                    }//####[1821]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1822]####
                }//####[1823]####
            }//####[1824]####
        }//####[1825]####
        __omp_vars.c = c;//####[1827]####
        __omp_vars.contrast = contrast;//####[1828]####
        __omp_vars.value = value;//####[1829]####
        __omp_vars.src = src;//####[1830]####
    }//####[1831]####
//####[1835]####
    private static volatile Method __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method = null;//####[1835]####
    private synchronized static void __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_ensureMethodVarSet() {//####[1835]####
        if (__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method == null) {//####[1835]####
            try {//####[1835]####
                __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_21", new Class[] {//####[1835]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing21.class//####[1835]####
                });//####[1835]####
            } catch (Exception e) {//####[1835]####
                e.printStackTrace();//####[1835]####
            }//####[1835]####
        }//####[1835]####
    }//####[1835]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(_omp__parallelRegionVarHolderClass_BitmapProcessing21 __omp_vars) {//####[1835]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1835]####
        return _ompParallelRegion_21(__omp_vars, new TaskInfo());//####[1835]####
    }//####[1835]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(_omp__parallelRegionVarHolderClass_BitmapProcessing21 __omp_vars, TaskInfo taskinfo) {//####[1835]####
        // ensure Method variable is set//####[1835]####
        if (__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method == null) {//####[1835]####
            __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_ensureMethodVarSet();//####[1835]####
        }//####[1835]####
        taskinfo.setParameters(__omp_vars);//####[1835]####
        taskinfo.setMethod(__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method);//####[1835]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1835]####
    }//####[1835]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing21> __omp_vars) {//####[1835]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1835]####
        return _ompParallelRegion_21(__omp_vars, new TaskInfo());//####[1835]####
    }//####[1835]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing21> __omp_vars, TaskInfo taskinfo) {//####[1835]####
        // ensure Method variable is set//####[1835]####
        if (__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method == null) {//####[1835]####
            __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_ensureMethodVarSet();//####[1835]####
        }//####[1835]####
        taskinfo.setTaskIdArgIndexes(0);//####[1835]####
        taskinfo.addDependsOn(__omp_vars);//####[1835]####
        taskinfo.setParameters(__omp_vars);//####[1835]####
        taskinfo.setMethod(__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method);//####[1835]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1835]####
    }//####[1835]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing21> __omp_vars) {//####[1835]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1835]####
        return _ompParallelRegion_21(__omp_vars, new TaskInfo());//####[1835]####
    }//####[1835]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing21> __omp_vars, TaskInfo taskinfo) {//####[1835]####
        // ensure Method variable is set//####[1835]####
        if (__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method == null) {//####[1835]####
            __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_ensureMethodVarSet();//####[1835]####
        }//####[1835]####
        taskinfo.setQueueArgIndexes(0);//####[1835]####
        taskinfo.setIsPipeline(true);//####[1835]####
        taskinfo.setParameters(__omp_vars);//####[1835]####
        taskinfo.setMethod(__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method);//####[1835]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1835]####
    }//####[1835]####
    public static void __pt___ompParallelRegion_21(_omp__parallelRegionVarHolderClass_BitmapProcessing21 __omp_vars) {//####[1835]####
        int G = __omp_vars.G;//####[1837]####
        int pixel = __omp_vars.pixel;//####[1838]####
        int A = __omp_vars.A;//####[1839]####
        Canvas c = __omp_vars.c;//####[1840]####
        int B = __omp_vars.B;//####[1841]####
        int width = __omp_vars.width;//####[1842]####
        double contrast = __omp_vars.contrast;//####[1843]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1844]####
        int height = __omp_vars.height;//####[1845]####
        int R = __omp_vars.R;//####[1846]####
        double value = __omp_vars.value;//####[1847]####
        Bitmap src = __omp_vars.src;//####[1848]####
        {//####[1849]####
            if (Pyjama.insideParallelRegion()) //####[1850]####
            {//####[1850]####
                boolean _omp_imFirst = _imFirst_23.getAndSet(false);//####[1852]####
                _holderForPIFirst = _imFirst_23;//####[1853]####
                if (_omp_imFirst) //####[1854]####
                {//####[1854]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing23_variables();//####[1855]####
                    int __omp_size_ = 0;//####[1856]####
                    for (int x = 0; x < width; x = x + 1) //####[1858]####
                    {//####[1858]####
                        _lastElement_23 = x;//####[1859]####
                        __omp_size_++;//####[1860]####
                    }//####[1861]####
                    _pi_23 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[1862]####
                    _omp_piVarContainer.add(_pi_23);//####[1863]####
                    _pi_23.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[1864]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.width = width;//####[1866]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.height = height;//####[1867]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.A = A;//####[1868]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.R = R;//####[1869]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.G = G;//####[1870]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.B = B;//####[1871]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.pixel = pixel;//####[1872]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.bmOut = bmOut;//####[1873]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.c = c;//####[1874]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.contrast = contrast;//####[1875]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.value = value;//####[1876]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.src = src;//####[1877]####
                    _waitBarrier_23.countDown();//####[1878]####
                } else {//####[1879]####
                    try {//####[1880]####
                        _waitBarrier_23.await();//####[1880]####
                    } catch (InterruptedException __omp__ie) {//####[1880]####
                        __omp__ie.printStackTrace();//####[1880]####
                    }//####[1880]####
                }//####[1881]####
                _ompWorkSharedUserCode_BitmapProcessing23(_ompWorkSharedUserCode_BitmapProcessing23_variables_instance);//####[1882]####
                if (_imFinishedCounter_23.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[1883]####
                {//####[1883]####
                    _waitBarrierAfter_23.countDown();//####[1884]####
                } else {//####[1885]####
                    try {//####[1886]####
                        _waitBarrierAfter_23.await();//####[1887]####
                    } catch (InterruptedException __omp__ie) {//####[1888]####
                        __omp__ie.printStackTrace();//####[1889]####
                    }//####[1890]####
                }//####[1891]####
            } else {//####[1894]####
                for (int x = 0; x < width; x = x + 1) //####[1896]####
                {//####[1896]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[1897]####
                    {//####[1897]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[1898]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[1899]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[1900]####
                        __omp_vars.R = (int) (((((__omp_vars.R / 255.0) - 0.5) * __omp_vars.contrast) + 0.5) * 255.0);//####[1901]####
                        if (__omp_vars.R < 0) //####[1902]####
                        {//####[1902]####
                            __omp_vars.R = 0;//####[1903]####
                        } else if (__omp_vars.R > 255) //####[1904]####
                        {//####[1904]####
                            __omp_vars.R = 255;//####[1905]####
                        }//####[1906]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[1907]####
                        __omp_vars.G = (int) (((((__omp_vars.G / 255.0) - 0.5) * __omp_vars.contrast) + 0.5) * 255.0);//####[1908]####
                        if (__omp_vars.G < 0) //####[1909]####
                        {//####[1909]####
                            __omp_vars.G = 0;//####[1910]####
                        } else if (__omp_vars.G > 255) //####[1911]####
                        {//####[1911]####
                            __omp_vars.G = 255;//####[1912]####
                        }//####[1913]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[1914]####
                        __omp_vars.B = (int) (((((__omp_vars.B / 255.0) - 0.5) * __omp_vars.contrast) + 0.5) * 255.0);//####[1915]####
                        if (__omp_vars.B < 0) //####[1916]####
                        {//####[1916]####
                            __omp_vars.B = 0;//####[1917]####
                        } else if (__omp_vars.B > 255) //####[1918]####
                        {//####[1918]####
                            __omp_vars.B = 255;//####[1919]####
                        }//####[1920]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[1921]####
                    }//####[1922]####
                }//####[1923]####
            }//####[1924]####
        }//####[1926]####
        __omp_vars.G = G;//####[1928]####
        __omp_vars.pixel = pixel;//####[1929]####
        __omp_vars.A = A;//####[1930]####
        __omp_vars.c = c;//####[1931]####
        __omp_vars.B = B;//####[1932]####
        __omp_vars.width = width;//####[1933]####
        __omp_vars.contrast = contrast;//####[1934]####
        __omp_vars.bmOut = bmOut;//####[1935]####
        __omp_vars.height = height;//####[1936]####
        __omp_vars.R = R;//####[1937]####
        __omp_vars.value = value;//####[1938]####
        __omp_vars.src = src;//####[1939]####
    }//####[1940]####
//####[1940]####
//####[1941]####
    public static Bitmap saturation(Bitmap src, int value) {//####[1941]####
        {//####[1941]####
            float f_value = (float) (value / 100.0);//####[1942]####
            int w = src.getWidth();//####[1943]####
            int h = src.getHeight();//####[1944]####
            Bitmap bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//####[1945]####
            Canvas canvasResult = new Canvas(bitmapResult);//####[1946]####
            Paint paint = new Paint();//####[1947]####
            ColorMatrix colorMatrix = new ColorMatrix();//####[1948]####
            colorMatrix.setSaturation(f_value);//####[1949]####
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);//####[1950]####
            paint.setColorFilter(filter);//####[1951]####
            canvasResult.drawBitmap(src, 0, 0, paint);//####[1952]####
            src.recycle();//####[1953]####
            src = null;//####[1954]####
            return bitmapResult;//####[1955]####
        }//####[1956]####
    }//####[1957]####
//####[1959]####
    public static Bitmap grayscale(Bitmap src) {//####[1959]####
        {//####[1959]####
            float[] GrayArray = { 0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f };//####[1960]####
            ColorMatrix colorMatrixGray = new ColorMatrix(GrayArray);//####[1961]####
            int w = src.getWidth();//####[1962]####
            int h = src.getHeight();//####[1963]####
            Bitmap bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//####[1964]####
            Canvas canvasResult = new Canvas(bitmapResult);//####[1965]####
            Paint paint = new Paint();//####[1966]####
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrixGray);//####[1967]####
            paint.setColorFilter(filter);//####[1968]####
            canvasResult.drawBitmap(src, 0, 0, paint);//####[1969]####
            src.recycle();//####[1970]####
            src = null;//####[1971]####
            return bitmapResult;//####[1972]####
        }//####[1973]####
    }//####[1974]####
//####[1976]####
    public static Bitmap vignette(Bitmap image) {//####[1976]####
        {//####[1976]####
            final int width = image.getWidth();//####[1977]####
            final int height = image.getHeight();//####[1978]####
            float radius = (float) (width / 1.2);//####[1979]####
            int[] colors = new int[] { 0, 0x55000000, 0xff000000 };//####[1980]####
            float[] positions = new float[] { 0.0f, 0.5f, 1.0f };//####[1981]####
            RadialGradient gradient = new RadialGradient(width / 2, height / 2, radius, colors, positions, Shader.TileMode.CLAMP);//####[1982]####
            Canvas canvas = new Canvas(image);//####[1983]####
            canvas.drawARGB(1, 0, 0, 0);//####[1984]####
            final Paint paint = new Paint();//####[1985]####
            paint.setAntiAlias(true);//####[1986]####
            paint.setColor(Color.BLACK);//####[1987]####
            paint.setShader(gradient);//####[1988]####
            final Rect rect = new Rect(0, 0, image.getWidth(), image.getHeight());//####[1989]####
            final RectF rectf = new RectF(rect);//####[1990]####
            canvas.drawRect(rectf, paint);//####[1991]####
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//####[1992]####
            canvas.drawBitmap(image, rect, rect, paint);//####[1993]####
            return image;//####[1994]####
        }//####[1995]####
    }//####[1996]####
//####[1998]####
    public static Bitmap hue(Bitmap bitmap, float hue) {//####[1998]####
        {//####[1998]####
            Bitmap newBitmap = bitmap.copy(bitmap.getConfig(), true);//####[1999]####
            int width = newBitmap.getWidth();//####[2000]####
            int height = newBitmap.getHeight();//####[2001]####
            float[] hsv = new float[3];//####[2002]####
            float _hue = hue;//####[2003]####
            if (Pyjama.insideParallelRegion()) //####[2005]####
            {//####[2005]####
                {//####[2007]####
                    for (int y = 0; y < height; y = y + 1) //####[2008]####
                    {//####[2008]####
                        for (int x = 0; x < width; x++) //####[2009]####
                        {//####[2009]####
                            int pixel = newBitmap.getPixel(x, y);//####[2010]####
                            Color.colorToHSV(pixel, hsv);//####[2011]####
                            hsv[0] = _hue;//####[2012]####
                            newBitmap.setPixel(x, y, Color.HSVToColor(Color.alpha(pixel), hsv));//####[2013]####
                        }//####[2014]####
                    }//####[2015]####
                }//####[2016]####
            } else {//####[2017]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[2019]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing24 _omp__parallelRegionVarHolderInstance_24 = new _omp__parallelRegionVarHolderClass_BitmapProcessing24();//####[2022]####
                _omp__parallelRegionVarHolderInstance_24.bitmap = bitmap;//####[2023]####
                _omp__parallelRegionVarHolderInstance_24.hue = hue;//####[2024]####
                _omp__parallelRegionVarHolderInstance_24.width = width;//####[2025]####
                _omp__parallelRegionVarHolderInstance_24.height = height;//####[2026]####
                _omp__parallelRegionVarHolderInstance_24._hue = _hue;//####[2027]####
                _omp__parallelRegionVarHolderInstance_24.hsv = hsv;//####[2028]####
                _omp__parallelRegionVarHolderInstance_24.newBitmap = newBitmap;//####[2029]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[2032]####
                TaskID _omp__parallelRegionTaskID_24 = _ompParallelRegion_24(_omp__parallelRegionVarHolderInstance_24);//####[2033]####
                __pt___ompParallelRegion_24(_omp__parallelRegionVarHolderInstance_24);//####[2034]####
                try {//####[2035]####
                    _omp__parallelRegionTaskID_24.waitTillFinished();//####[2035]####
                } catch (Exception __pt__ex) {//####[2035]####
                    __pt__ex.printStackTrace();//####[2035]####
                }//####[2035]####
                PJPackageOnly.setMasterThread(null);//####[2037]####
                _holderForPIFirst.set(true);//####[2038]####
                bitmap = _omp__parallelRegionVarHolderInstance_24.bitmap;//####[2040]####
                hue = _omp__parallelRegionVarHolderInstance_24.hue;//####[2041]####
                width = _omp__parallelRegionVarHolderInstance_24.width;//####[2042]####
                height = _omp__parallelRegionVarHolderInstance_24.height;//####[2043]####
                _hue = _omp__parallelRegionVarHolderInstance_24._hue;//####[2044]####
                hsv = _omp__parallelRegionVarHolderInstance_24.hsv;//####[2045]####
                newBitmap = _omp__parallelRegionVarHolderInstance_24.newBitmap;//####[2046]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[2047]####
            }//####[2048]####
            bitmap.recycle();//####[2051]####
            bitmap = null;//####[2052]####
            return newBitmap;//####[2053]####
        }//####[2054]####
    }//####[2055]####
//####[2056]####
    private static AtomicBoolean _imFirst_26 = new AtomicBoolean(true);//####[2056]####
//####[2057]####
    private static AtomicInteger _imFinishedCounter_26 = new AtomicInteger(0);//####[2057]####
//####[2058]####
    private static CountDownLatch _waitBarrier_26 = new CountDownLatch(1);//####[2058]####
//####[2059]####
    private static CountDownLatch _waitBarrierAfter_26 = new CountDownLatch(1);//####[2059]####
//####[2060]####
    private static ParIterator<Integer> _pi_26 = null;//####[2060]####
//####[2061]####
    private static Integer _lastElement_26 = null;//####[2061]####
//####[2062]####
    private static _ompWorkSharedUserCode_BitmapProcessing26_variables _ompWorkSharedUserCode_BitmapProcessing26_variables_instance = null;//####[2062]####
//####[2063]####
    private static void _ompWorkSharedUserCode_BitmapProcessing26(_ompWorkSharedUserCode_BitmapProcessing26_variables __omp_vars) {//####[2063]####
        int width = __omp_vars.width;//####[2065]####
        int height = __omp_vars.height;//####[2066]####
        float[] hsv = __omp_vars.hsv;//####[2067]####
        float _hue = __omp_vars._hue;//####[2068]####
        Bitmap newBitmap = __omp_vars.newBitmap;//####[2069]####
        Bitmap bitmap = __omp_vars.bitmap;//####[2070]####
        float hue = __omp_vars.hue;//####[2071]####
        Integer y;//####[2072]####
        while (_pi_26.hasNext()) //####[2073]####
        {//####[2073]####
            y = _pi_26.next();//####[2074]####
            {//####[2076]####
                for (int x = 0; x < width; x++) //####[2077]####
                {//####[2077]####
                    int pixel = newBitmap.getPixel(x, y);//####[2078]####
                    Color.colorToHSV(pixel, hsv);//####[2079]####
                    hsv[0] = _hue;//####[2080]####
                    newBitmap.setPixel(x, y, Color.HSVToColor(Color.alpha(pixel), hsv));//####[2081]####
                }//####[2082]####
            }//####[2083]####
        }//####[2084]####
        __omp_vars.bitmap = bitmap;//####[2086]####
        __omp_vars.hue = hue;//####[2087]####
    }//####[2088]####
//####[2092]####
    private static volatile Method __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method = null;//####[2092]####
    private synchronized static void __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_ensureMethodVarSet() {//####[2092]####
        if (__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method == null) {//####[2092]####
            try {//####[2092]####
                __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_24", new Class[] {//####[2092]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing24.class//####[2092]####
                });//####[2092]####
            } catch (Exception e) {//####[2092]####
                e.printStackTrace();//####[2092]####
            }//####[2092]####
        }//####[2092]####
    }//####[2092]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(_omp__parallelRegionVarHolderClass_BitmapProcessing24 __omp_vars) {//####[2092]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2092]####
        return _ompParallelRegion_24(__omp_vars, new TaskInfo());//####[2092]####
    }//####[2092]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(_omp__parallelRegionVarHolderClass_BitmapProcessing24 __omp_vars, TaskInfo taskinfo) {//####[2092]####
        // ensure Method variable is set//####[2092]####
        if (__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method == null) {//####[2092]####
            __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_ensureMethodVarSet();//####[2092]####
        }//####[2092]####
        taskinfo.setParameters(__omp_vars);//####[2092]####
        taskinfo.setMethod(__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method);//####[2092]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2092]####
    }//####[2092]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing24> __omp_vars) {//####[2092]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2092]####
        return _ompParallelRegion_24(__omp_vars, new TaskInfo());//####[2092]####
    }//####[2092]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing24> __omp_vars, TaskInfo taskinfo) {//####[2092]####
        // ensure Method variable is set//####[2092]####
        if (__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method == null) {//####[2092]####
            __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_ensureMethodVarSet();//####[2092]####
        }//####[2092]####
        taskinfo.setTaskIdArgIndexes(0);//####[2092]####
        taskinfo.addDependsOn(__omp_vars);//####[2092]####
        taskinfo.setParameters(__omp_vars);//####[2092]####
        taskinfo.setMethod(__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method);//####[2092]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2092]####
    }//####[2092]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing24> __omp_vars) {//####[2092]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2092]####
        return _ompParallelRegion_24(__omp_vars, new TaskInfo());//####[2092]####
    }//####[2092]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing24> __omp_vars, TaskInfo taskinfo) {//####[2092]####
        // ensure Method variable is set//####[2092]####
        if (__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method == null) {//####[2092]####
            __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_ensureMethodVarSet();//####[2092]####
        }//####[2092]####
        taskinfo.setQueueArgIndexes(0);//####[2092]####
        taskinfo.setIsPipeline(true);//####[2092]####
        taskinfo.setParameters(__omp_vars);//####[2092]####
        taskinfo.setMethod(__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method);//####[2092]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2092]####
    }//####[2092]####
    public static void __pt___ompParallelRegion_24(_omp__parallelRegionVarHolderClass_BitmapProcessing24 __omp_vars) {//####[2092]####
        Bitmap bitmap = __omp_vars.bitmap;//####[2094]####
        float hue = __omp_vars.hue;//####[2095]####
        int width = __omp_vars.width;//####[2096]####
        int height = __omp_vars.height;//####[2097]####
        float _hue = __omp_vars._hue;//####[2098]####
        float[] hsv = __omp_vars.hsv;//####[2099]####
        Bitmap newBitmap = __omp_vars.newBitmap;//####[2100]####
        {//####[2101]####
            if (Pyjama.insideParallelRegion()) //####[2102]####
            {//####[2102]####
                boolean _omp_imFirst = _imFirst_26.getAndSet(false);//####[2104]####
                _holderForPIFirst = _imFirst_26;//####[2105]####
                if (_omp_imFirst) //####[2106]####
                {//####[2106]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing26_variables();//####[2107]####
                    int __omp_size_ = 0;//####[2108]####
                    for (int y = 0; y < height; y = y + 1) //####[2110]####
                    {//####[2110]####
                        _lastElement_26 = y;//####[2111]####
                        __omp_size_++;//####[2112]####
                    }//####[2113]####
                    _pi_26 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[2114]####
                    _omp_piVarContainer.add(_pi_26);//####[2115]####
                    _pi_26.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[2116]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.width = width;//####[2118]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.height = height;//####[2119]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.hsv = hsv;//####[2120]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance._hue = _hue;//####[2121]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.newBitmap = newBitmap;//####[2122]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.bitmap = bitmap;//####[2123]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.hue = hue;//####[2124]####
                    _waitBarrier_26.countDown();//####[2125]####
                } else {//####[2126]####
                    try {//####[2127]####
                        _waitBarrier_26.await();//####[2127]####
                    } catch (InterruptedException __omp__ie) {//####[2127]####
                        __omp__ie.printStackTrace();//####[2127]####
                    }//####[2127]####
                }//####[2128]####
                _ompWorkSharedUserCode_BitmapProcessing26(_ompWorkSharedUserCode_BitmapProcessing26_variables_instance);//####[2129]####
                if (_imFinishedCounter_26.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[2130]####
                {//####[2130]####
                    _waitBarrierAfter_26.countDown();//####[2131]####
                } else {//####[2132]####
                    try {//####[2133]####
                        _waitBarrierAfter_26.await();//####[2134]####
                    } catch (InterruptedException __omp__ie) {//####[2135]####
                        __omp__ie.printStackTrace();//####[2136]####
                    }//####[2137]####
                }//####[2138]####
            } else {//####[2141]####
                for (int y = 0; y < height; y = y + 1) //####[2143]####
                {//####[2143]####
                    for (int x = 0; x < __omp_vars.width; x++) //####[2144]####
                    {//####[2144]####
                        int pixel = __omp_vars.newBitmap.getPixel(x, y);//####[2145]####
                        Color.colorToHSV(pixel, __omp_vars.hsv);//####[2146]####
                        __omp_vars.hsv[0] = __omp_vars._hue;//####[2147]####
                        __omp_vars.newBitmap.setPixel(x, y, Color.HSVToColor(Color.alpha(pixel), __omp_vars.hsv));//####[2148]####
                    }//####[2149]####
                }//####[2150]####
            }//####[2151]####
        }//####[2153]####
        __omp_vars.bitmap = bitmap;//####[2155]####
        __omp_vars.hue = hue;//####[2156]####
        __omp_vars.width = width;//####[2157]####
        __omp_vars.height = height;//####[2158]####
        __omp_vars._hue = _hue;//####[2159]####
        __omp_vars.hsv = hsv;//####[2160]####
        __omp_vars.newBitmap = newBitmap;//####[2161]####
    }//####[2162]####
//####[2162]####
//####[2163]####
    public static Bitmap tint(Bitmap src, int color) {//####[2163]####
        {//####[2163]####
            int width = src.getWidth();//####[2164]####
            int height = src.getHeight();//####[2165]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[2166]####
            Paint p = new Paint(Color.RED);//####[2167]####
            ColorFilter filter = new LightingColorFilter(color, 1);//####[2168]####
            p.setColorFilter(filter);//####[2169]####
            Canvas c = new Canvas();//####[2170]####
            c.setBitmap(bmOut);//####[2171]####
            c.drawBitmap(src, 0, 0, p);//####[2172]####
            src.recycle();//####[2173]####
            src = null;//####[2174]####
            return bmOut;//####[2175]####
        }//####[2176]####
    }//####[2177]####
//####[2179]####
    public static Bitmap invert(Bitmap src) {//####[2179]####
        {//####[2179]####
            Bitmap output = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());//####[2180]####
            int A = 0, R = 0, G = 0, B = 0;//####[2181]####
            int pixelColor = 0;//####[2182]####
            int height = src.getHeight();//####[2183]####
            int width = src.getWidth();//####[2184]####
            if (Pyjama.insideParallelRegion()) //####[2186]####
            {//####[2186]####
                {//####[2188]####
                    for (int y = 0; y < height; y = y + 1) //####[2189]####
                    {//####[2189]####
                        for (int x = 0; x < width; x++) //####[2190]####
                        {//####[2190]####
                            pixelColor = src.getPixel(x, y);//####[2191]####
                            A = Color.alpha(pixelColor);//####[2192]####
                            R = 255 - Color.red(pixelColor);//####[2193]####
                            G = 255 - Color.green(pixelColor);//####[2194]####
                            B = 255 - Color.blue(pixelColor);//####[2195]####
                            output.setPixel(x, y, Color.argb(A, R, G, B));//####[2196]####
                        }//####[2197]####
                    }//####[2198]####
                }//####[2199]####
            } else {//####[2200]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[2202]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing27 _omp__parallelRegionVarHolderInstance_27 = new _omp__parallelRegionVarHolderClass_BitmapProcessing27();//####[2205]####
                _omp__parallelRegionVarHolderInstance_27.G = G;//####[2206]####
                _omp__parallelRegionVarHolderInstance_27.A = A;//####[2207]####
                _omp__parallelRegionVarHolderInstance_27.B = B;//####[2208]####
                _omp__parallelRegionVarHolderInstance_27.width = width;//####[2209]####
                _omp__parallelRegionVarHolderInstance_27.height = height;//####[2210]####
                _omp__parallelRegionVarHolderInstance_27.R = R;//####[2211]####
                _omp__parallelRegionVarHolderInstance_27.pixelColor = pixelColor;//####[2212]####
                _omp__parallelRegionVarHolderInstance_27.src = src;//####[2213]####
                _omp__parallelRegionVarHolderInstance_27.output = output;//####[2214]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[2217]####
                TaskID _omp__parallelRegionTaskID_27 = _ompParallelRegion_27(_omp__parallelRegionVarHolderInstance_27);//####[2218]####
                __pt___ompParallelRegion_27(_omp__parallelRegionVarHolderInstance_27);//####[2219]####
                try {//####[2220]####
                    _omp__parallelRegionTaskID_27.waitTillFinished();//####[2220]####
                } catch (Exception __pt__ex) {//####[2220]####
                    __pt__ex.printStackTrace();//####[2220]####
                }//####[2220]####
                PJPackageOnly.setMasterThread(null);//####[2222]####
                _holderForPIFirst.set(true);//####[2223]####
                G = _omp__parallelRegionVarHolderInstance_27.G;//####[2225]####
                A = _omp__parallelRegionVarHolderInstance_27.A;//####[2226]####
                B = _omp__parallelRegionVarHolderInstance_27.B;//####[2227]####
                width = _omp__parallelRegionVarHolderInstance_27.width;//####[2228]####
                height = _omp__parallelRegionVarHolderInstance_27.height;//####[2229]####
                R = _omp__parallelRegionVarHolderInstance_27.R;//####[2230]####
                pixelColor = _omp__parallelRegionVarHolderInstance_27.pixelColor;//####[2231]####
                src = _omp__parallelRegionVarHolderInstance_27.src;//####[2232]####
                output = _omp__parallelRegionVarHolderInstance_27.output;//####[2233]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[2234]####
            }//####[2235]####
            src.recycle();//####[2238]####
            src = null;//####[2239]####
            return output;//####[2240]####
        }//####[2241]####
    }//####[2242]####
//####[2243]####
    private static AtomicBoolean _imFirst_29 = new AtomicBoolean(true);//####[2243]####
//####[2244]####
    private static AtomicInteger _imFinishedCounter_29 = new AtomicInteger(0);//####[2244]####
//####[2245]####
    private static CountDownLatch _waitBarrier_29 = new CountDownLatch(1);//####[2245]####
//####[2246]####
    private static CountDownLatch _waitBarrierAfter_29 = new CountDownLatch(1);//####[2246]####
//####[2247]####
    private static ParIterator<Integer> _pi_29 = null;//####[2247]####
//####[2248]####
    private static Integer _lastElement_29 = null;//####[2248]####
//####[2249]####
    private static _ompWorkSharedUserCode_BitmapProcessing29_variables _ompWorkSharedUserCode_BitmapProcessing29_variables_instance = null;//####[2249]####
//####[2250]####
    private static void _ompWorkSharedUserCode_BitmapProcessing29(_ompWorkSharedUserCode_BitmapProcessing29_variables __omp_vars) {//####[2250]####
        int width = __omp_vars.width;//####[2252]####
        int height = __omp_vars.height;//####[2253]####
        int A = __omp_vars.A;//####[2254]####
        int R = __omp_vars.R;//####[2255]####
        int G = __omp_vars.G;//####[2256]####
        int B = __omp_vars.B;//####[2257]####
        int pixelColor = __omp_vars.pixelColor;//####[2258]####
        Bitmap src = __omp_vars.src;//####[2259]####
        Bitmap output = __omp_vars.output;//####[2260]####
        Integer y;//####[2261]####
        while (_pi_29.hasNext()) //####[2262]####
        {//####[2262]####
            y = _pi_29.next();//####[2263]####
            {//####[2265]####
                for (int x = 0; x < width; x++) //####[2266]####
                {//####[2266]####
                    pixelColor = src.getPixel(x, y);//####[2267]####
                    A = Color.alpha(pixelColor);//####[2268]####
                    R = 255 - Color.red(pixelColor);//####[2269]####
                    G = 255 - Color.green(pixelColor);//####[2270]####
                    B = 255 - Color.blue(pixelColor);//####[2271]####
                    output.setPixel(x, y, Color.argb(A, R, G, B));//####[2272]####
                }//####[2273]####
            }//####[2274]####
        }//####[2275]####
        __omp_vars.src = src;//####[2277]####
        __omp_vars.output = output;//####[2278]####
    }//####[2279]####
//####[2283]####
    private static volatile Method __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method = null;//####[2283]####
    private synchronized static void __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_ensureMethodVarSet() {//####[2283]####
        if (__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method == null) {//####[2283]####
            try {//####[2283]####
                __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_27", new Class[] {//####[2283]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing27.class//####[2283]####
                });//####[2283]####
            } catch (Exception e) {//####[2283]####
                e.printStackTrace();//####[2283]####
            }//####[2283]####
        }//####[2283]####
    }//####[2283]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(_omp__parallelRegionVarHolderClass_BitmapProcessing27 __omp_vars) {//####[2283]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2283]####
        return _ompParallelRegion_27(__omp_vars, new TaskInfo());//####[2283]####
    }//####[2283]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(_omp__parallelRegionVarHolderClass_BitmapProcessing27 __omp_vars, TaskInfo taskinfo) {//####[2283]####
        // ensure Method variable is set//####[2283]####
        if (__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method == null) {//####[2283]####
            __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_ensureMethodVarSet();//####[2283]####
        }//####[2283]####
        taskinfo.setParameters(__omp_vars);//####[2283]####
        taskinfo.setMethod(__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method);//####[2283]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2283]####
    }//####[2283]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing27> __omp_vars) {//####[2283]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2283]####
        return _ompParallelRegion_27(__omp_vars, new TaskInfo());//####[2283]####
    }//####[2283]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing27> __omp_vars, TaskInfo taskinfo) {//####[2283]####
        // ensure Method variable is set//####[2283]####
        if (__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method == null) {//####[2283]####
            __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_ensureMethodVarSet();//####[2283]####
        }//####[2283]####
        taskinfo.setTaskIdArgIndexes(0);//####[2283]####
        taskinfo.addDependsOn(__omp_vars);//####[2283]####
        taskinfo.setParameters(__omp_vars);//####[2283]####
        taskinfo.setMethod(__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method);//####[2283]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2283]####
    }//####[2283]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing27> __omp_vars) {//####[2283]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2283]####
        return _ompParallelRegion_27(__omp_vars, new TaskInfo());//####[2283]####
    }//####[2283]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing27> __omp_vars, TaskInfo taskinfo) {//####[2283]####
        // ensure Method variable is set//####[2283]####
        if (__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method == null) {//####[2283]####
            __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_ensureMethodVarSet();//####[2283]####
        }//####[2283]####
        taskinfo.setQueueArgIndexes(0);//####[2283]####
        taskinfo.setIsPipeline(true);//####[2283]####
        taskinfo.setParameters(__omp_vars);//####[2283]####
        taskinfo.setMethod(__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method);//####[2283]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2283]####
    }//####[2283]####
    public static void __pt___ompParallelRegion_27(_omp__parallelRegionVarHolderClass_BitmapProcessing27 __omp_vars) {//####[2283]####
        int G = __omp_vars.G;//####[2285]####
        int A = __omp_vars.A;//####[2286]####
        int B = __omp_vars.B;//####[2287]####
        int width = __omp_vars.width;//####[2288]####
        int height = __omp_vars.height;//####[2289]####
        int R = __omp_vars.R;//####[2290]####
        int pixelColor = __omp_vars.pixelColor;//####[2291]####
        Bitmap src = __omp_vars.src;//####[2292]####
        Bitmap output = __omp_vars.output;//####[2293]####
        {//####[2294]####
            if (Pyjama.insideParallelRegion()) //####[2295]####
            {//####[2295]####
                boolean _omp_imFirst = _imFirst_29.getAndSet(false);//####[2297]####
                _holderForPIFirst = _imFirst_29;//####[2298]####
                if (_omp_imFirst) //####[2299]####
                {//####[2299]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing29_variables();//####[2300]####
                    int __omp_size_ = 0;//####[2301]####
                    for (int y = 0; y < height; y = y + 1) //####[2303]####
                    {//####[2303]####
                        _lastElement_29 = y;//####[2304]####
                        __omp_size_++;//####[2305]####
                    }//####[2306]####
                    _pi_29 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[2307]####
                    _omp_piVarContainer.add(_pi_29);//####[2308]####
                    _pi_29.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[2309]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.width = width;//####[2311]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.height = height;//####[2312]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.A = A;//####[2313]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.R = R;//####[2314]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.G = G;//####[2315]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.B = B;//####[2316]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.pixelColor = pixelColor;//####[2317]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.src = src;//####[2318]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.output = output;//####[2319]####
                    _waitBarrier_29.countDown();//####[2320]####
                } else {//####[2321]####
                    try {//####[2322]####
                        _waitBarrier_29.await();//####[2322]####
                    } catch (InterruptedException __omp__ie) {//####[2322]####
                        __omp__ie.printStackTrace();//####[2322]####
                    }//####[2322]####
                }//####[2323]####
                _ompWorkSharedUserCode_BitmapProcessing29(_ompWorkSharedUserCode_BitmapProcessing29_variables_instance);//####[2324]####
                if (_imFinishedCounter_29.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[2325]####
                {//####[2325]####
                    _waitBarrierAfter_29.countDown();//####[2326]####
                } else {//####[2327]####
                    try {//####[2328]####
                        _waitBarrierAfter_29.await();//####[2329]####
                    } catch (InterruptedException __omp__ie) {//####[2330]####
                        __omp__ie.printStackTrace();//####[2331]####
                    }//####[2332]####
                }//####[2333]####
            } else {//####[2336]####
                for (int y = 0; y < height; y = y + 1) //####[2338]####
                {//####[2338]####
                    for (int x = 0; x < __omp_vars.width; x++) //####[2339]####
                    {//####[2339]####
                        __omp_vars.pixelColor = __omp_vars.src.getPixel(x, y);//####[2340]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixelColor);//####[2341]####
                        __omp_vars.R = 255 - Color.red(__omp_vars.pixelColor);//####[2342]####
                        __omp_vars.G = 255 - Color.green(__omp_vars.pixelColor);//####[2343]####
                        __omp_vars.B = 255 - Color.blue(__omp_vars.pixelColor);//####[2344]####
                        __omp_vars.output.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[2345]####
                    }//####[2346]####
                }//####[2347]####
            }//####[2348]####
        }//####[2350]####
        __omp_vars.G = G;//####[2352]####
        __omp_vars.A = A;//####[2353]####
        __omp_vars.B = B;//####[2354]####
        __omp_vars.width = width;//####[2355]####
        __omp_vars.height = height;//####[2356]####
        __omp_vars.R = R;//####[2357]####
        __omp_vars.pixelColor = pixelColor;//####[2358]####
        __omp_vars.src = src;//####[2359]####
        __omp_vars.output = output;//####[2360]####
    }//####[2361]####
//####[2361]####
//####[2362]####
    public static Bitmap boost(Bitmap src, int type, float percent) {//####[2362]####
        {//####[2362]####
            percent = (float) percent / 100;//####[2363]####
            int width = src.getWidth();//####[2364]####
            int height = src.getHeight();//####[2365]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[2366]####
            int A = 0, R = 0, G = 0, B = 0;//####[2367]####
            int pixel = 0;//####[2368]####
            if (Pyjama.insideParallelRegion()) //####[2370]####
            {//####[2370]####
                {//####[2372]####
                    for (int x = 0; x < width; x = x + 1) //####[2373]####
                    {//####[2373]####
                        for (int y = 0; y < height; ++y) //####[2374]####
                        {//####[2374]####
                            pixel = src.getPixel(x, y);//####[2375]####
                            A = Color.alpha(pixel);//####[2376]####
                            R = Color.red(pixel);//####[2377]####
                            G = Color.green(pixel);//####[2378]####
                            B = Color.blue(pixel);//####[2379]####
                            if (type == 1) //####[2380]####
                            {//####[2380]####
                                R = (int) (R * (1 + percent));//####[2381]####
                                if (R > 255) //####[2382]####
                                R = 255;//####[2382]####
                            } else if (type == 2) //####[2383]####
                            {//####[2383]####
                                G = (int) (G * (1 + percent));//####[2384]####
                                if (G > 255) //####[2385]####
                                G = 255;//####[2385]####
                            } else if (type == 3) //####[2386]####
                            {//####[2386]####
                                B = (int) (B * (1 + percent));//####[2387]####
                                if (B > 255) //####[2388]####
                                B = 255;//####[2388]####
                            }//####[2389]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[2390]####
                        }//####[2391]####
                    }//####[2392]####
                }//####[2393]####
            } else {//####[2394]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[2396]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing30 _omp__parallelRegionVarHolderInstance_30 = new _omp__parallelRegionVarHolderClass_BitmapProcessing30();//####[2399]####
                _omp__parallelRegionVarHolderInstance_30.G = G;//####[2400]####
                _omp__parallelRegionVarHolderInstance_30.percent = percent;//####[2401]####
                _omp__parallelRegionVarHolderInstance_30.pixel = pixel;//####[2402]####
                _omp__parallelRegionVarHolderInstance_30.A = A;//####[2403]####
                _omp__parallelRegionVarHolderInstance_30.B = B;//####[2404]####
                _omp__parallelRegionVarHolderInstance_30.width = width;//####[2405]####
                _omp__parallelRegionVarHolderInstance_30.type = type;//####[2406]####
                _omp__parallelRegionVarHolderInstance_30.bmOut = bmOut;//####[2407]####
                _omp__parallelRegionVarHolderInstance_30.height = height;//####[2408]####
                _omp__parallelRegionVarHolderInstance_30.R = R;//####[2409]####
                _omp__parallelRegionVarHolderInstance_30.src = src;//####[2410]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[2413]####
                TaskID _omp__parallelRegionTaskID_30 = _ompParallelRegion_30(_omp__parallelRegionVarHolderInstance_30);//####[2414]####
                __pt___ompParallelRegion_30(_omp__parallelRegionVarHolderInstance_30);//####[2415]####
                try {//####[2416]####
                    _omp__parallelRegionTaskID_30.waitTillFinished();//####[2416]####
                } catch (Exception __pt__ex) {//####[2416]####
                    __pt__ex.printStackTrace();//####[2416]####
                }//####[2416]####
                PJPackageOnly.setMasterThread(null);//####[2418]####
                _holderForPIFirst.set(true);//####[2419]####
                G = _omp__parallelRegionVarHolderInstance_30.G;//####[2421]####
                percent = _omp__parallelRegionVarHolderInstance_30.percent;//####[2422]####
                pixel = _omp__parallelRegionVarHolderInstance_30.pixel;//####[2423]####
                A = _omp__parallelRegionVarHolderInstance_30.A;//####[2424]####
                B = _omp__parallelRegionVarHolderInstance_30.B;//####[2425]####
                width = _omp__parallelRegionVarHolderInstance_30.width;//####[2426]####
                type = _omp__parallelRegionVarHolderInstance_30.type;//####[2427]####
                bmOut = _omp__parallelRegionVarHolderInstance_30.bmOut;//####[2428]####
                height = _omp__parallelRegionVarHolderInstance_30.height;//####[2429]####
                R = _omp__parallelRegionVarHolderInstance_30.R;//####[2430]####
                src = _omp__parallelRegionVarHolderInstance_30.src;//####[2431]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[2432]####
            }//####[2433]####
            src.recycle();//####[2436]####
            src = null;//####[2437]####
            return bmOut;//####[2438]####
        }//####[2439]####
    }//####[2440]####
//####[2441]####
    private static AtomicBoolean _imFirst_32 = new AtomicBoolean(true);//####[2441]####
//####[2442]####
    private static AtomicInteger _imFinishedCounter_32 = new AtomicInteger(0);//####[2442]####
//####[2443]####
    private static CountDownLatch _waitBarrier_32 = new CountDownLatch(1);//####[2443]####
//####[2444]####
    private static CountDownLatch _waitBarrierAfter_32 = new CountDownLatch(1);//####[2444]####
//####[2445]####
    private static ParIterator<Integer> _pi_32 = null;//####[2445]####
//####[2446]####
    private static Integer _lastElement_32 = null;//####[2446]####
//####[2447]####
    private static _ompWorkSharedUserCode_BitmapProcessing32_variables _ompWorkSharedUserCode_BitmapProcessing32_variables_instance = null;//####[2447]####
//####[2448]####
    private static void _ompWorkSharedUserCode_BitmapProcessing32(_ompWorkSharedUserCode_BitmapProcessing32_variables __omp_vars) {//####[2448]####
        int width = __omp_vars.width;//####[2450]####
        int height = __omp_vars.height;//####[2451]####
        int A = __omp_vars.A;//####[2452]####
        int R = __omp_vars.R;//####[2453]####
        int G = __omp_vars.G;//####[2454]####
        int B = __omp_vars.B;//####[2455]####
        int pixel = __omp_vars.pixel;//####[2456]####
        int type = __omp_vars.type;//####[2457]####
        float percent = __omp_vars.percent;//####[2458]####
        Bitmap bmOut = __omp_vars.bmOut;//####[2459]####
        Bitmap src = __omp_vars.src;//####[2460]####
        Integer x;//####[2461]####
        while (_pi_32.hasNext()) //####[2462]####
        {//####[2462]####
            x = _pi_32.next();//####[2463]####
            {//####[2465]####
                for (int y = 0; y < height; ++y) //####[2466]####
                {//####[2466]####
                    pixel = src.getPixel(x, y);//####[2467]####
                    A = Color.alpha(pixel);//####[2468]####
                    R = Color.red(pixel);//####[2469]####
                    G = Color.green(pixel);//####[2470]####
                    B = Color.blue(pixel);//####[2471]####
                    if (type == 1) //####[2472]####
                    {//####[2472]####
                        R = (int) (R * (1 + percent));//####[2473]####
                        if (R > 255) //####[2474]####
                        R = 255;//####[2474]####
                    } else if (type == 2) //####[2475]####
                    {//####[2475]####
                        G = (int) (G * (1 + percent));//####[2476]####
                        if (G > 255) //####[2477]####
                        G = 255;//####[2477]####
                    } else if (type == 3) //####[2478]####
                    {//####[2478]####
                        B = (int) (B * (1 + percent));//####[2479]####
                        if (B > 255) //####[2480]####
                        B = 255;//####[2480]####
                    }//####[2481]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[2482]####
                }//####[2483]####
            }//####[2484]####
        }//####[2485]####
        __omp_vars.percent = percent;//####[2487]####
        __omp_vars.bmOut = bmOut;//####[2488]####
        __omp_vars.src = src;//####[2489]####
    }//####[2490]####
//####[2494]####
    private static volatile Method __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method = null;//####[2494]####
    private synchronized static void __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_ensureMethodVarSet() {//####[2494]####
        if (__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method == null) {//####[2494]####
            try {//####[2494]####
                __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_30", new Class[] {//####[2494]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing30.class//####[2494]####
                });//####[2494]####
            } catch (Exception e) {//####[2494]####
                e.printStackTrace();//####[2494]####
            }//####[2494]####
        }//####[2494]####
    }//####[2494]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(_omp__parallelRegionVarHolderClass_BitmapProcessing30 __omp_vars) {//####[2494]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2494]####
        return _ompParallelRegion_30(__omp_vars, new TaskInfo());//####[2494]####
    }//####[2494]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(_omp__parallelRegionVarHolderClass_BitmapProcessing30 __omp_vars, TaskInfo taskinfo) {//####[2494]####
        // ensure Method variable is set//####[2494]####
        if (__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method == null) {//####[2494]####
            __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_ensureMethodVarSet();//####[2494]####
        }//####[2494]####
        taskinfo.setParameters(__omp_vars);//####[2494]####
        taskinfo.setMethod(__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method);//####[2494]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2494]####
    }//####[2494]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing30> __omp_vars) {//####[2494]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2494]####
        return _ompParallelRegion_30(__omp_vars, new TaskInfo());//####[2494]####
    }//####[2494]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing30> __omp_vars, TaskInfo taskinfo) {//####[2494]####
        // ensure Method variable is set//####[2494]####
        if (__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method == null) {//####[2494]####
            __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_ensureMethodVarSet();//####[2494]####
        }//####[2494]####
        taskinfo.setTaskIdArgIndexes(0);//####[2494]####
        taskinfo.addDependsOn(__omp_vars);//####[2494]####
        taskinfo.setParameters(__omp_vars);//####[2494]####
        taskinfo.setMethod(__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method);//####[2494]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2494]####
    }//####[2494]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing30> __omp_vars) {//####[2494]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2494]####
        return _ompParallelRegion_30(__omp_vars, new TaskInfo());//####[2494]####
    }//####[2494]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing30> __omp_vars, TaskInfo taskinfo) {//####[2494]####
        // ensure Method variable is set//####[2494]####
        if (__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method == null) {//####[2494]####
            __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_ensureMethodVarSet();//####[2494]####
        }//####[2494]####
        taskinfo.setQueueArgIndexes(0);//####[2494]####
        taskinfo.setIsPipeline(true);//####[2494]####
        taskinfo.setParameters(__omp_vars);//####[2494]####
        taskinfo.setMethod(__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method);//####[2494]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2494]####
    }//####[2494]####
    public static void __pt___ompParallelRegion_30(_omp__parallelRegionVarHolderClass_BitmapProcessing30 __omp_vars) {//####[2494]####
        int G = __omp_vars.G;//####[2496]####
        float percent = __omp_vars.percent;//####[2497]####
        int pixel = __omp_vars.pixel;//####[2498]####
        int A = __omp_vars.A;//####[2499]####
        int B = __omp_vars.B;//####[2500]####
        int width = __omp_vars.width;//####[2501]####
        int type = __omp_vars.type;//####[2502]####
        Bitmap bmOut = __omp_vars.bmOut;//####[2503]####
        int height = __omp_vars.height;//####[2504]####
        int R = __omp_vars.R;//####[2505]####
        Bitmap src = __omp_vars.src;//####[2506]####
        {//####[2507]####
            if (Pyjama.insideParallelRegion()) //####[2508]####
            {//####[2508]####
                boolean _omp_imFirst = _imFirst_32.getAndSet(false);//####[2510]####
                _holderForPIFirst = _imFirst_32;//####[2511]####
                if (_omp_imFirst) //####[2512]####
                {//####[2512]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing32_variables();//####[2513]####
                    int __omp_size_ = 0;//####[2514]####
                    for (int x = 0; x < width; x = x + 1) //####[2516]####
                    {//####[2516]####
                        _lastElement_32 = x;//####[2517]####
                        __omp_size_++;//####[2518]####
                    }//####[2519]####
                    _pi_32 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 160, false);//####[2520]####
                    _omp_piVarContainer.add(_pi_32);//####[2521]####
                    _pi_32.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[2522]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.width = width;//####[2524]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.height = height;//####[2525]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.A = A;//####[2526]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.R = R;//####[2527]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.G = G;//####[2528]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.B = B;//####[2529]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.pixel = pixel;//####[2530]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.type = type;//####[2531]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.percent = percent;//####[2532]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.bmOut = bmOut;//####[2533]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.src = src;//####[2534]####
                    _waitBarrier_32.countDown();//####[2535]####
                } else {//####[2536]####
                    try {//####[2537]####
                        _waitBarrier_32.await();//####[2537]####
                    } catch (InterruptedException __omp__ie) {//####[2537]####
                        __omp__ie.printStackTrace();//####[2537]####
                    }//####[2537]####
                }//####[2538]####
                _ompWorkSharedUserCode_BitmapProcessing32(_ompWorkSharedUserCode_BitmapProcessing32_variables_instance);//####[2539]####
                if (_imFinishedCounter_32.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[2540]####
                {//####[2540]####
                    _waitBarrierAfter_32.countDown();//####[2541]####
                } else {//####[2542]####
                    try {//####[2543]####
                        _waitBarrierAfter_32.await();//####[2544]####
                    } catch (InterruptedException __omp__ie) {//####[2545]####
                        __omp__ie.printStackTrace();//####[2546]####
                    }//####[2547]####
                }//####[2548]####
            } else {//####[2551]####
                for (int x = 0; x < width; x = x + 1) //####[2553]####
                {//####[2553]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[2554]####
                    {//####[2554]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[2555]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[2556]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[2557]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[2558]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[2559]####
                        if (__omp_vars.type == 1) //####[2560]####
                        {//####[2560]####
                            __omp_vars.R = (int) (__omp_vars.R * (1 + __omp_vars.percent));//####[2561]####
                            if (__omp_vars.R > 255) //####[2562]####
                            __omp_vars.R = 255;//####[2562]####
                        } else if (__omp_vars.type == 2) //####[2563]####
                        {//####[2563]####
                            __omp_vars.G = (int) (__omp_vars.G * (1 + __omp_vars.percent));//####[2564]####
                            if (__omp_vars.G > 255) //####[2565]####
                            __omp_vars.G = 255;//####[2565]####
                        } else if (__omp_vars.type == 3) //####[2566]####
                        {//####[2566]####
                            __omp_vars.B = (int) (__omp_vars.B * (1 + __omp_vars.percent));//####[2567]####
                            if (__omp_vars.B > 255) //####[2568]####
                            __omp_vars.B = 255;//####[2568]####
                        }//####[2569]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[2570]####
                    }//####[2571]####
                }//####[2572]####
            }//####[2573]####
        }//####[2575]####
        __omp_vars.G = G;//####[2577]####
        __omp_vars.percent = percent;//####[2578]####
        __omp_vars.pixel = pixel;//####[2579]####
        __omp_vars.A = A;//####[2580]####
        __omp_vars.B = B;//####[2581]####
        __omp_vars.width = width;//####[2582]####
        __omp_vars.type = type;//####[2583]####
        __omp_vars.bmOut = bmOut;//####[2584]####
        __omp_vars.height = height;//####[2585]####
        __omp_vars.R = R;//####[2586]####
        __omp_vars.src = src;//####[2587]####
    }//####[2588]####
//####[2588]####
//####[2589]####
    public static final Bitmap sketch(Bitmap src) {//####[2589]####
        {//####[2589]####
            int type = 6;//####[2590]####
            int threshold = 130;//####[2591]####
            int width = src.getWidth();//####[2592]####
            int height = src.getHeight();//####[2593]####
            Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());//####[2594]####
            int A = 0, R = 0, G = 0, B = 0;//####[2595]####
            int sumR = 0, sumG = 0, sumB = 0;//####[2596]####
            int[][] pixels = new int[3][3];//####[2597]####
            for (int y = 0; y < height - 2; ++y) //####[2598]####
            {//####[2598]####
                for (int x = 0; x < width - 2; ++x) //####[2599]####
                {//####[2599]####
                    for (int i = 0; i < 3; ++i) //####[2600]####
                    {//####[2600]####
                        for (int j = 0; j < 3; ++j) //####[2601]####
                        {//####[2601]####
                            pixels[i][j] = src.getPixel(x + i, y + j);//####[2602]####
                        }//####[2603]####
                    }//####[2604]####
                    A = Color.alpha(pixels[1][1]);//####[2605]####
                    sumR = sumG = sumB = 0;//####[2606]####
                    sumR = (type * Color.red(pixels[1][1])) - Color.red(pixels[0][0]) - Color.red(pixels[0][2]) - Color.red(pixels[2][0]) - Color.red(pixels[2][2]);//####[2607]####
                    sumG = (type * Color.green(pixels[1][1])) - Color.green(pixels[0][0]) - Color.green(pixels[0][2]) - Color.green(pixels[2][0]) - Color.green(pixels[2][2]);//####[2608]####
                    sumB = (type * Color.blue(pixels[1][1])) - Color.blue(pixels[0][0]) - Color.blue(pixels[0][2]) - Color.blue(pixels[2][0]) - Color.blue(pixels[2][2]);//####[2609]####
                    R = (int) (sumR + threshold);//####[2610]####
                    if (R < 0) //####[2611]####
                    {//####[2611]####
                        R = 0;//####[2612]####
                    } else if (R > 255) //####[2613]####
                    {//####[2613]####
                        R = 255;//####[2614]####
                    }//####[2615]####
                    G = (int) (sumG + threshold);//####[2616]####
                    if (G < 0) //####[2617]####
                    {//####[2617]####
                        G = 0;//####[2618]####
                    } else if (G > 255) //####[2619]####
                    {//####[2619]####
                        G = 255;//####[2620]####
                    }//####[2621]####
                    B = (int) (sumB + threshold);//####[2622]####
                    if (B < 0) //####[2623]####
                    {//####[2623]####
                        B = 0;//####[2624]####
                    } else if (B > 255) //####[2625]####
                    {//####[2625]####
                        B = 255;//####[2626]####
                    }//####[2627]####
                    result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));//####[2628]####
                }//####[2629]####
            }//####[2630]####
            src.recycle();//####[2631]####
            src = null;//####[2632]####
            return result;//####[2633]####
        }//####[2634]####
    }//####[2635]####
//####[2637]####
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_url) throws IOException {//####[2637]####
        {//####[2637]####
            ExifInterface ei = new ExifInterface(image_url);//####[2638]####
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);//####[2639]####
            switch(orientation) {//####[2640]####
                case ExifInterface.ORIENTATION_ROTATE_90://####[2640]####
                    return rotate(bitmap, 90);//####[2642]####
                case ExifInterface.ORIENTATION_ROTATE_180://####[2642]####
                    return rotate(bitmap, 180);//####[2644]####
                case ExifInterface.ORIENTATION_ROTATE_270://####[2644]####
                    return rotate(bitmap, 270);//####[2646]####
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL://####[2646]####
                    return flip(bitmap, true, false);//####[2648]####
                case ExifInterface.ORIENTATION_FLIP_VERTICAL://####[2648]####
                    return flip(bitmap, false, true);//####[2650]####
                default://####[2650]####
                    return bitmap;//####[2652]####
            }//####[2652]####
        }//####[2654]####
    }//####[2655]####
}//####[2655]####
