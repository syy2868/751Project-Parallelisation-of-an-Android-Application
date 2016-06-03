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
                _omp__parallelRegionVarHolderInstance_0.width = width;//####[129]####
                _omp__parallelRegionVarHolderInstance_0.height = height;//####[130]####
                _omp__parallelRegionVarHolderInstance_0.A = A;//####[131]####
                _omp__parallelRegionVarHolderInstance_0.R = R;//####[132]####
                _omp__parallelRegionVarHolderInstance_0.G = G;//####[133]####
                _omp__parallelRegionVarHolderInstance_0.B = B;//####[134]####
                _omp__parallelRegionVarHolderInstance_0.pixel = pixel;//####[135]####
                _omp__parallelRegionVarHolderInstance_0.red = red;//####[136]####
                _omp__parallelRegionVarHolderInstance_0.green = green;//####[137]####
                _omp__parallelRegionVarHolderInstance_0.blue = blue;//####[138]####
                _omp__parallelRegionVarHolderInstance_0.bmOut = bmOut;//####[139]####
                _omp__parallelRegionVarHolderInstance_0.src = src;//####[140]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[143]####
                TaskID _omp__parallelRegionTaskID_0 = _ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[144]####
                __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[145]####
                try {//####[146]####
                    _omp__parallelRegionTaskID_0.waitTillFinished();//####[146]####
                } catch (Exception __pt__ex) {//####[146]####
                    __pt__ex.printStackTrace();//####[146]####
                }//####[146]####
                PJPackageOnly.setMasterThread(null);//####[148]####
                _holderForPIFirst.set(true);//####[149]####
                width = _omp__parallelRegionVarHolderInstance_0.width;//####[151]####
                height = _omp__parallelRegionVarHolderInstance_0.height;//####[152]####
                A = _omp__parallelRegionVarHolderInstance_0.A;//####[153]####
                R = _omp__parallelRegionVarHolderInstance_0.R;//####[154]####
                G = _omp__parallelRegionVarHolderInstance_0.G;//####[155]####
                B = _omp__parallelRegionVarHolderInstance_0.B;//####[156]####
                pixel = _omp__parallelRegionVarHolderInstance_0.pixel;//####[157]####
                red = _omp__parallelRegionVarHolderInstance_0.red;//####[158]####
                green = _omp__parallelRegionVarHolderInstance_0.green;//####[159]####
                blue = _omp__parallelRegionVarHolderInstance_0.blue;//####[160]####
                bmOut = _omp__parallelRegionVarHolderInstance_0.bmOut;//####[161]####
                src = _omp__parallelRegionVarHolderInstance_0.src;//####[162]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[163]####
            }//####[164]####
            src.recycle();//####[167]####
            src = null;//####[168]####
            return bmOut;//####[169]####
        }//####[170]####
    }//####[171]####
//####[172]####
    private static AtomicBoolean _imFirst_2 = new AtomicBoolean(true);//####[172]####
//####[173]####
    private static AtomicInteger _imFinishedCounter_2 = new AtomicInteger(0);//####[173]####
//####[174]####
    private static CountDownLatch _waitBarrier_2 = new CountDownLatch(1);//####[174]####
//####[175]####
    private static CountDownLatch _waitBarrierAfter_2 = new CountDownLatch(1);//####[175]####
//####[176]####
    private static ParIterator<Integer> _pi_2 = null;//####[176]####
//####[177]####
    private static Integer _lastElement_2 = null;//####[177]####
//####[178]####
    private static _ompWorkSharedUserCode_BitmapProcessing2_variables _ompWorkSharedUserCode_BitmapProcessing2_variables_instance = null;//####[178]####
//####[179]####
    private static void _ompWorkSharedUserCode_BitmapProcessing2(_ompWorkSharedUserCode_BitmapProcessing2_variables __omp_vars) {//####[179]####
        Bitmap bmOut = __omp_vars.bmOut;//####[181]####
        int G = __omp_vars.G;//####[182]####
        int height = __omp_vars.height;//####[183]####
        double red = __omp_vars.red;//####[184]####
        int pixel = __omp_vars.pixel;//####[185]####
        int A = __omp_vars.A;//####[186]####
        double blue = __omp_vars.blue;//####[187]####
        int B = __omp_vars.B;//####[188]####
        double green = __omp_vars.green;//####[189]####
        int R = __omp_vars.R;//####[190]####
        int width = __omp_vars.width;//####[191]####
        Bitmap src = __omp_vars.src;//####[192]####
        Integer x;//####[193]####
        while (_pi_2.hasNext()) //####[194]####
        {//####[194]####
            x = _pi_2.next();//####[195]####
            {//####[197]####
                for (int y = 0; y < height; ++y) //####[198]####
                {//####[198]####
                    pixel = src.getPixel(x, y);//####[199]####
                    A = Color.alpha(pixel);//####[200]####
                    R = (int) (Color.red(pixel) * red);//####[201]####
                    G = (int) (Color.green(pixel) * green);//####[202]####
                    B = (int) (Color.blue(pixel) * blue);//####[203]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[204]####
                }//####[205]####
            }//####[206]####
        }//####[207]####
        __omp_vars.bmOut = bmOut;//####[209]####
        __omp_vars.G = G;//####[210]####
        __omp_vars.height = height;//####[211]####
        __omp_vars.red = red;//####[212]####
        __omp_vars.pixel = pixel;//####[213]####
        __omp_vars.A = A;//####[214]####
        __omp_vars.blue = blue;//####[215]####
        __omp_vars.B = B;//####[216]####
        __omp_vars.green = green;//####[217]####
        __omp_vars.R = R;//####[218]####
        __omp_vars.width = width;//####[219]####
        __omp_vars.src = src;//####[220]####
    }//####[221]####
//####[225]####
    private static volatile Method __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method = null;//####[225]####
    private synchronized static void __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_ensureMethodVarSet() {//####[225]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method == null) {//####[225]####
            try {//####[225]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_0", new Class[] {//####[225]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing0.class//####[225]####
                });//####[225]####
            } catch (Exception e) {//####[225]####
                e.printStackTrace();//####[225]####
            }//####[225]####
        }//####[225]####
    }//####[225]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_BitmapProcessing0 __omp_vars) {//####[225]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[225]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[225]####
    }//####[225]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_BitmapProcessing0 __omp_vars, TaskInfo taskinfo) {//####[225]####
        // ensure Method variable is set//####[225]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method == null) {//####[225]####
            __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_ensureMethodVarSet();//####[225]####
        }//####[225]####
        taskinfo.setParameters(__omp_vars);//####[225]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method);//####[225]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[225]####
    }//####[225]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing0> __omp_vars) {//####[225]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[225]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[225]####
    }//####[225]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing0> __omp_vars, TaskInfo taskinfo) {//####[225]####
        // ensure Method variable is set//####[225]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method == null) {//####[225]####
            __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_ensureMethodVarSet();//####[225]####
        }//####[225]####
        taskinfo.setTaskIdArgIndexes(0);//####[225]####
        taskinfo.addDependsOn(__omp_vars);//####[225]####
        taskinfo.setParameters(__omp_vars);//####[225]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method);//####[225]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[225]####
    }//####[225]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing0> __omp_vars) {//####[225]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[225]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[225]####
    }//####[225]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing0> __omp_vars, TaskInfo taskinfo) {//####[225]####
        // ensure Method variable is set//####[225]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method == null) {//####[225]####
            __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_ensureMethodVarSet();//####[225]####
        }//####[225]####
        taskinfo.setQueueArgIndexes(0);//####[225]####
        taskinfo.setIsPipeline(true);//####[225]####
        taskinfo.setParameters(__omp_vars);//####[225]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_BitmapProcessing0_method);//####[225]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[225]####
    }//####[225]####
    public static void __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderClass_BitmapProcessing0 __omp_vars) {//####[225]####
        int width = __omp_vars.width;//####[227]####
        int height = __omp_vars.height;//####[228]####
        int A = __omp_vars.A;//####[229]####
        int R = __omp_vars.R;//####[230]####
        int G = __omp_vars.G;//####[231]####
        int B = __omp_vars.B;//####[232]####
        int pixel = __omp_vars.pixel;//####[233]####
        double red = __omp_vars.red;//####[234]####
        double green = __omp_vars.green;//####[235]####
        double blue = __omp_vars.blue;//####[236]####
        Bitmap bmOut = __omp_vars.bmOut;//####[237]####
        Bitmap src = __omp_vars.src;//####[238]####
        {//####[239]####
            if (Pyjama.insideParallelRegion()) //####[240]####
            {//####[240]####
                boolean _omp_imFirst = _imFirst_2.getAndSet(false);//####[242]####
                _holderForPIFirst = _imFirst_2;//####[243]####
                if (_omp_imFirst) //####[244]####
                {//####[244]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing2_variables();//####[245]####
                    int __omp_size_ = 0;//####[246]####
                    for (int x = 0; x < width; x = x + 1) //####[248]####
                    {//####[248]####
                        _lastElement_2 = x;//####[249]####
                        __omp_size_++;//####[250]####
                    }//####[251]####
                    _pi_2 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[252]####
                    _omp_piVarContainer.add(_pi_2);//####[253]####
                    _pi_2.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[254]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.bmOut = bmOut;//####[255]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.G = G;//####[256]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.height = height;//####[257]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.red = red;//####[258]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.pixel = pixel;//####[259]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.A = A;//####[260]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.blue = blue;//####[261]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.B = B;//####[262]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.green = green;//####[263]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.R = R;//####[264]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.width = width;//####[265]####
                    _ompWorkSharedUserCode_BitmapProcessing2_variables_instance.src = src;//####[266]####
                    _waitBarrier_2.countDown();//####[267]####
                } else {//####[268]####
                    try {//####[269]####
                        _waitBarrier_2.await();//####[269]####
                    } catch (InterruptedException __omp__ie) {//####[269]####
                        __omp__ie.printStackTrace();//####[269]####
                    }//####[269]####
                }//####[270]####
                _ompWorkSharedUserCode_BitmapProcessing2(_ompWorkSharedUserCode_BitmapProcessing2_variables_instance);//####[271]####
                if (_imFinishedCounter_2.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[272]####
                {//####[272]####
                    _waitBarrierAfter_2.countDown();//####[273]####
                } else {//####[274]####
                    try {//####[275]####
                        _waitBarrierAfter_2.await();//####[276]####
                    } catch (InterruptedException __omp__ie) {//####[277]####
                        __omp__ie.printStackTrace();//####[278]####
                    }//####[279]####
                }//####[280]####
            } else {//####[283]####
                for (int x = 0; x < width; x = x + 1) //####[285]####
                {//####[285]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[286]####
                    {//####[286]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[287]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[288]####
                        __omp_vars.R = (int) (Color.red(__omp_vars.pixel) * __omp_vars.red);//####[289]####
                        __omp_vars.G = (int) (Color.green(__omp_vars.pixel) * __omp_vars.green);//####[290]####
                        __omp_vars.B = (int) (Color.blue(__omp_vars.pixel) * __omp_vars.blue);//####[291]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[292]####
                    }//####[293]####
                }//####[294]####
            }//####[295]####
        }//####[297]####
        __omp_vars.src = src;//####[299]####
    }//####[300]####
//####[300]####
//####[301]####
    public static Bitmap gaussian(Bitmap src) {//####[301]####
        {//####[301]####
            double[][] GaussianBlurConfig = new double[][] { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };//####[302]####
            ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);//####[303]####
            convMatrix.applyConfig(GaussianBlurConfig);//####[304]####
            convMatrix.Factor = 16;//####[305]####
            convMatrix.Offset = 0;//####[306]####
            return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);//####[307]####
        }//####[308]####
    }//####[309]####
//####[311]####
    public static Bitmap cdepth(Bitmap src, int bitOffset) {//####[311]####
        {//####[311]####
            int width = src.getWidth();//####[312]####
            int height = src.getHeight();//####[313]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[314]####
            int A = 0, R = 0, G = 0, B = 0;//####[315]####
            int pixel = 0;//####[316]####
            if (Pyjama.insideParallelRegion()) //####[318]####
            {//####[318]####
                {//####[320]####
                    for (int x = 0; x < width; x = x + 1) //####[321]####
                    {//####[321]####
                        for (int y = 0; y < height; ++y) //####[322]####
                        {//####[322]####
                            pixel = src.getPixel(x, y);//####[323]####
                            A = Color.alpha(pixel);//####[324]####
                            R = Color.red(pixel);//####[325]####
                            G = Color.green(pixel);//####[326]####
                            B = Color.blue(pixel);//####[327]####
                            R = ((R + (bitOffset / 2)) - ((R + (bitOffset / 2)) % bitOffset) - 1);//####[328]####
                            if (R < 0) //####[329]####
                            {//####[329]####
                                R = 0;//####[330]####
                            }//####[331]####
                            G = ((G + (bitOffset / 2)) - ((G + (bitOffset / 2)) % bitOffset) - 1);//####[332]####
                            if (G < 0) //####[333]####
                            {//####[333]####
                                G = 0;//####[334]####
                            }//####[335]####
                            B = ((B + (bitOffset / 2)) - ((B + (bitOffset / 2)) % bitOffset) - 1);//####[336]####
                            if (B < 0) //####[337]####
                            {//####[337]####
                                B = 0;//####[338]####
                            }//####[339]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[340]####
                        }//####[341]####
                    }//####[342]####
                }//####[343]####
            } else {//####[344]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[346]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing3 _omp__parallelRegionVarHolderInstance_3 = new _omp__parallelRegionVarHolderClass_BitmapProcessing3();//####[349]####
                _omp__parallelRegionVarHolderInstance_3.width = width;//####[352]####
                _omp__parallelRegionVarHolderInstance_3.height = height;//####[353]####
                _omp__parallelRegionVarHolderInstance_3.A = A;//####[354]####
                _omp__parallelRegionVarHolderInstance_3.R = R;//####[355]####
                _omp__parallelRegionVarHolderInstance_3.G = G;//####[356]####
                _omp__parallelRegionVarHolderInstance_3.B = B;//####[357]####
                _omp__parallelRegionVarHolderInstance_3.pixel = pixel;//####[358]####
                _omp__parallelRegionVarHolderInstance_3.bitOffset = bitOffset;//####[359]####
                _omp__parallelRegionVarHolderInstance_3.bmOut = bmOut;//####[360]####
                _omp__parallelRegionVarHolderInstance_3.src = src;//####[361]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[364]####
                TaskID _omp__parallelRegionTaskID_3 = _ompParallelRegion_3(_omp__parallelRegionVarHolderInstance_3);//####[365]####
                __pt___ompParallelRegion_3(_omp__parallelRegionVarHolderInstance_3);//####[366]####
                try {//####[367]####
                    _omp__parallelRegionTaskID_3.waitTillFinished();//####[367]####
                } catch (Exception __pt__ex) {//####[367]####
                    __pt__ex.printStackTrace();//####[367]####
                }//####[367]####
                PJPackageOnly.setMasterThread(null);//####[369]####
                _holderForPIFirst.set(true);//####[370]####
                width = _omp__parallelRegionVarHolderInstance_3.width;//####[372]####
                height = _omp__parallelRegionVarHolderInstance_3.height;//####[373]####
                A = _omp__parallelRegionVarHolderInstance_3.A;//####[374]####
                R = _omp__parallelRegionVarHolderInstance_3.R;//####[375]####
                G = _omp__parallelRegionVarHolderInstance_3.G;//####[376]####
                B = _omp__parallelRegionVarHolderInstance_3.B;//####[377]####
                pixel = _omp__parallelRegionVarHolderInstance_3.pixel;//####[378]####
                bitOffset = _omp__parallelRegionVarHolderInstance_3.bitOffset;//####[379]####
                bmOut = _omp__parallelRegionVarHolderInstance_3.bmOut;//####[380]####
                src = _omp__parallelRegionVarHolderInstance_3.src;//####[381]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[382]####
            }//####[383]####
            src.recycle();//####[386]####
            src = null;//####[387]####
            return bmOut;//####[388]####
        }//####[389]####
    }//####[390]####
//####[391]####
    private static AtomicBoolean _imFirst_5 = new AtomicBoolean(true);//####[391]####
//####[392]####
    private static AtomicInteger _imFinishedCounter_5 = new AtomicInteger(0);//####[392]####
//####[393]####
    private static CountDownLatch _waitBarrier_5 = new CountDownLatch(1);//####[393]####
//####[394]####
    private static CountDownLatch _waitBarrierAfter_5 = new CountDownLatch(1);//####[394]####
//####[395]####
    private static ParIterator<Integer> _pi_5 = null;//####[395]####
//####[396]####
    private static Integer _lastElement_5 = null;//####[396]####
//####[397]####
    private static _ompWorkSharedUserCode_BitmapProcessing5_variables _ompWorkSharedUserCode_BitmapProcessing5_variables_instance = null;//####[397]####
//####[398]####
    private static void _ompWorkSharedUserCode_BitmapProcessing5(_ompWorkSharedUserCode_BitmapProcessing5_variables __omp_vars) {//####[398]####
        Bitmap bmOut = __omp_vars.bmOut;//####[400]####
        int G = __omp_vars.G;//####[401]####
        int height = __omp_vars.height;//####[402]####
        int pixel = __omp_vars.pixel;//####[403]####
        int A = __omp_vars.A;//####[404]####
        int B = __omp_vars.B;//####[405]####
        int R = __omp_vars.R;//####[406]####
        int width = __omp_vars.width;//####[407]####
        int bitOffset = __omp_vars.bitOffset;//####[408]####
        Bitmap src = __omp_vars.src;//####[409]####
        Integer x;//####[410]####
        while (_pi_5.hasNext()) //####[411]####
        {//####[411]####
            x = _pi_5.next();//####[412]####
            {//####[414]####
                for (int y = 0; y < height; ++y) //####[415]####
                {//####[415]####
                    pixel = src.getPixel(x, y);//####[416]####
                    A = Color.alpha(pixel);//####[417]####
                    R = Color.red(pixel);//####[418]####
                    G = Color.green(pixel);//####[419]####
                    B = Color.blue(pixel);//####[420]####
                    R = ((R + (bitOffset / 2)) - ((R + (bitOffset / 2)) % bitOffset) - 1);//####[421]####
                    if (R < 0) //####[422]####
                    {//####[422]####
                        R = 0;//####[423]####
                    }//####[424]####
                    G = ((G + (bitOffset / 2)) - ((G + (bitOffset / 2)) % bitOffset) - 1);//####[425]####
                    if (G < 0) //####[426]####
                    {//####[426]####
                        G = 0;//####[427]####
                    }//####[428]####
                    B = ((B + (bitOffset / 2)) - ((B + (bitOffset / 2)) % bitOffset) - 1);//####[429]####
                    if (B < 0) //####[430]####
                    {//####[430]####
                        B = 0;//####[431]####
                    }//####[432]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[433]####
                }//####[434]####
            }//####[435]####
        }//####[436]####
        __omp_vars.bmOut = bmOut;//####[438]####
        __omp_vars.G = G;//####[439]####
        __omp_vars.height = height;//####[440]####
        __omp_vars.pixel = pixel;//####[441]####
        __omp_vars.A = A;//####[442]####
        __omp_vars.B = B;//####[443]####
        __omp_vars.R = R;//####[444]####
        __omp_vars.width = width;//####[445]####
        __omp_vars.bitOffset = bitOffset;//####[446]####
        __omp_vars.src = src;//####[447]####
    }//####[448]####
//####[452]####
    private static volatile Method __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method = null;//####[452]####
    private synchronized static void __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_ensureMethodVarSet() {//####[452]####
        if (__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method == null) {//####[452]####
            try {//####[452]####
                __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_3", new Class[] {//####[452]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing3.class//####[452]####
                });//####[452]####
            } catch (Exception e) {//####[452]####
                e.printStackTrace();//####[452]####
            }//####[452]####
        }//####[452]####
    }//####[452]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(_omp__parallelRegionVarHolderClass_BitmapProcessing3 __omp_vars) {//####[452]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[452]####
        return _ompParallelRegion_3(__omp_vars, new TaskInfo());//####[452]####
    }//####[452]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(_omp__parallelRegionVarHolderClass_BitmapProcessing3 __omp_vars, TaskInfo taskinfo) {//####[452]####
        // ensure Method variable is set//####[452]####
        if (__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method == null) {//####[452]####
            __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_ensureMethodVarSet();//####[452]####
        }//####[452]####
        taskinfo.setParameters(__omp_vars);//####[452]####
        taskinfo.setMethod(__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method);//####[452]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[452]####
    }//####[452]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing3> __omp_vars) {//####[452]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[452]####
        return _ompParallelRegion_3(__omp_vars, new TaskInfo());//####[452]####
    }//####[452]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing3> __omp_vars, TaskInfo taskinfo) {//####[452]####
        // ensure Method variable is set//####[452]####
        if (__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method == null) {//####[452]####
            __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_ensureMethodVarSet();//####[452]####
        }//####[452]####
        taskinfo.setTaskIdArgIndexes(0);//####[452]####
        taskinfo.addDependsOn(__omp_vars);//####[452]####
        taskinfo.setParameters(__omp_vars);//####[452]####
        taskinfo.setMethod(__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method);//####[452]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[452]####
    }//####[452]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing3> __omp_vars) {//####[452]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[452]####
        return _ompParallelRegion_3(__omp_vars, new TaskInfo());//####[452]####
    }//####[452]####
    private static TaskIDGroup<Void> _ompParallelRegion_3(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing3> __omp_vars, TaskInfo taskinfo) {//####[452]####
        // ensure Method variable is set//####[452]####
        if (__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method == null) {//####[452]####
            __pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_ensureMethodVarSet();//####[452]####
        }//####[452]####
        taskinfo.setQueueArgIndexes(0);//####[452]####
        taskinfo.setIsPipeline(true);//####[452]####
        taskinfo.setParameters(__omp_vars);//####[452]####
        taskinfo.setMethod(__pt___ompParallelRegion_3__omp__parallelRegionVarHolderClass_BitmapProcessing3_method);//####[452]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[452]####
    }//####[452]####
    public static void __pt___ompParallelRegion_3(_omp__parallelRegionVarHolderClass_BitmapProcessing3 __omp_vars) {//####[452]####
        int width = __omp_vars.width;//####[454]####
        int height = __omp_vars.height;//####[455]####
        int A = __omp_vars.A;//####[456]####
        int R = __omp_vars.R;//####[457]####
        int G = __omp_vars.G;//####[458]####
        int B = __omp_vars.B;//####[459]####
        int pixel = __omp_vars.pixel;//####[460]####
        int bitOffset = __omp_vars.bitOffset;//####[461]####
        Bitmap bmOut = __omp_vars.bmOut;//####[462]####
        Bitmap src = __omp_vars.src;//####[463]####
        {//####[464]####
            if (Pyjama.insideParallelRegion()) //####[465]####
            {//####[465]####
                boolean _omp_imFirst = _imFirst_5.getAndSet(false);//####[467]####
                _holderForPIFirst = _imFirst_5;//####[468]####
                if (_omp_imFirst) //####[469]####
                {//####[469]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing5_variables();//####[470]####
                    int __omp_size_ = 0;//####[471]####
                    for (int x = 0; x < width; x = x + 1) //####[473]####
                    {//####[473]####
                        _lastElement_5 = x;//####[474]####
                        __omp_size_++;//####[475]####
                    }//####[476]####
                    _pi_5 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[477]####
                    _omp_piVarContainer.add(_pi_5);//####[478]####
                    _pi_5.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[479]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.bmOut = bmOut;//####[480]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.G = G;//####[481]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.height = height;//####[482]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.pixel = pixel;//####[483]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.A = A;//####[484]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.B = B;//####[485]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.R = R;//####[486]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.width = width;//####[487]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.bitOffset = bitOffset;//####[488]####
                    _ompWorkSharedUserCode_BitmapProcessing5_variables_instance.src = src;//####[489]####
                    _waitBarrier_5.countDown();//####[490]####
                } else {//####[491]####
                    try {//####[492]####
                        _waitBarrier_5.await();//####[492]####
                    } catch (InterruptedException __omp__ie) {//####[492]####
                        __omp__ie.printStackTrace();//####[492]####
                    }//####[492]####
                }//####[493]####
                _ompWorkSharedUserCode_BitmapProcessing5(_ompWorkSharedUserCode_BitmapProcessing5_variables_instance);//####[494]####
                if (_imFinishedCounter_5.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[495]####
                {//####[495]####
                    _waitBarrierAfter_5.countDown();//####[496]####
                } else {//####[497]####
                    try {//####[498]####
                        _waitBarrierAfter_5.await();//####[499]####
                    } catch (InterruptedException __omp__ie) {//####[500]####
                        __omp__ie.printStackTrace();//####[501]####
                    }//####[502]####
                }//####[503]####
            } else {//####[506]####
                for (int x = 0; x < width; x = x + 1) //####[508]####
                {//####[508]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[509]####
                    {//####[509]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[510]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[511]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[512]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[513]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[514]####
                        __omp_vars.R = ((__omp_vars.R + (__omp_vars.bitOffset / 2)) - ((__omp_vars.R + (__omp_vars.bitOffset / 2)) % __omp_vars.bitOffset) - 1);//####[515]####
                        if (__omp_vars.R < 0) //####[516]####
                        {//####[516]####
                            __omp_vars.R = 0;//####[517]####
                        }//####[518]####
                        __omp_vars.G = ((__omp_vars.G + (__omp_vars.bitOffset / 2)) - ((__omp_vars.G + (__omp_vars.bitOffset / 2)) % __omp_vars.bitOffset) - 1);//####[519]####
                        if (__omp_vars.G < 0) //####[520]####
                        {//####[520]####
                            __omp_vars.G = 0;//####[521]####
                        }//####[522]####
                        __omp_vars.B = ((__omp_vars.B + (__omp_vars.bitOffset / 2)) - ((__omp_vars.B + (__omp_vars.bitOffset / 2)) % __omp_vars.bitOffset) - 1);//####[523]####
                        if (__omp_vars.B < 0) //####[524]####
                        {//####[524]####
                            __omp_vars.B = 0;//####[525]####
                        }//####[526]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[527]####
                    }//####[528]####
                }//####[529]####
            }//####[530]####
        }//####[532]####
        __omp_vars.src = src;//####[534]####
    }//####[535]####
//####[535]####
//####[536]####
    public static Bitmap sharpen(Bitmap src) {//####[536]####
        {//####[536]####
            double[][] SharpConfig = new double[][] { { 0, -2, 0 }, { -2, 11, -2 }, { 0, -2, 0 } };//####[537]####
            ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);//####[538]####
            convMatrix.applyConfig(SharpConfig);//####[539]####
            convMatrix.Factor = 3;//####[540]####
            return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);//####[541]####
        }//####[542]####
    }//####[543]####
//####[545]####
    public static Bitmap noise(Bitmap source) {//####[545]####
        {//####[545]####
            int COLOR_MAX = 0xFF;//####[546]####
            int width = source.getWidth();//####[547]####
            int height = source.getHeight();//####[548]####
            int[] pixels = new int[width * height];//####[549]####
            source.getPixels(pixels, 0, width, 0, 0, width, height);//####[550]####
            Random random = new Random();//####[551]####
            int index = 0;//####[552]####
            if (Pyjama.insideParallelRegion()) //####[554]####
            {//####[554]####
                {//####[556]####
                    for (int y = 0; y < height; y = y + 1) //####[557]####
                    {//####[557]####
                        for (int x = 0; x < width; ++x) //####[558]####
                        {//####[558]####
                            index = y * width + x;//####[559]####
                            int randColor = Color.rgb(random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));//####[560]####
                            pixels[index] |= randColor;//####[561]####
                        }//####[562]####
                    }//####[563]####
                }//####[564]####
            } else {//####[565]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[567]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing6 _omp__parallelRegionVarHolderInstance_6 = new _omp__parallelRegionVarHolderClass_BitmapProcessing6();//####[570]####
                _omp__parallelRegionVarHolderInstance_6.width = width;//####[573]####
                _omp__parallelRegionVarHolderInstance_6.height = height;//####[574]####
                _omp__parallelRegionVarHolderInstance_6.pixels = pixels;//####[575]####
                _omp__parallelRegionVarHolderInstance_6.COLOR_MAX = COLOR_MAX;//####[576]####
                _omp__parallelRegionVarHolderInstance_6.index = index;//####[577]####
                _omp__parallelRegionVarHolderInstance_6.source = source;//####[578]####
                _omp__parallelRegionVarHolderInstance_6.random = random;//####[579]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[582]####
                TaskID _omp__parallelRegionTaskID_6 = _ompParallelRegion_6(_omp__parallelRegionVarHolderInstance_6);//####[583]####
                __pt___ompParallelRegion_6(_omp__parallelRegionVarHolderInstance_6);//####[584]####
                try {//####[585]####
                    _omp__parallelRegionTaskID_6.waitTillFinished();//####[585]####
                } catch (Exception __pt__ex) {//####[585]####
                    __pt__ex.printStackTrace();//####[585]####
                }//####[585]####
                PJPackageOnly.setMasterThread(null);//####[587]####
                _holderForPIFirst.set(true);//####[588]####
                width = _omp__parallelRegionVarHolderInstance_6.width;//####[590]####
                height = _omp__parallelRegionVarHolderInstance_6.height;//####[591]####
                pixels = _omp__parallelRegionVarHolderInstance_6.pixels;//####[592]####
                COLOR_MAX = _omp__parallelRegionVarHolderInstance_6.COLOR_MAX;//####[593]####
                index = _omp__parallelRegionVarHolderInstance_6.index;//####[594]####
                source = _omp__parallelRegionVarHolderInstance_6.source;//####[595]####
                random = _omp__parallelRegionVarHolderInstance_6.random;//####[596]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[597]####
            }//####[598]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, source.getConfig());//####[601]####
            bmOut.setPixels(pixels, 0, width, 0, 0, width, height);//####[602]####
            source.recycle();//####[603]####
            source = null;//####[604]####
            return bmOut;//####[605]####
        }//####[606]####
    }//####[607]####
//####[608]####
    private static AtomicBoolean _imFirst_8 = new AtomicBoolean(true);//####[608]####
//####[609]####
    private static AtomicInteger _imFinishedCounter_8 = new AtomicInteger(0);//####[609]####
//####[610]####
    private static CountDownLatch _waitBarrier_8 = new CountDownLatch(1);//####[610]####
//####[611]####
    private static CountDownLatch _waitBarrierAfter_8 = new CountDownLatch(1);//####[611]####
//####[612]####
    private static ParIterator<Integer> _pi_8 = null;//####[612]####
//####[613]####
    private static Integer _lastElement_8 = null;//####[613]####
//####[614]####
    private static _ompWorkSharedUserCode_BitmapProcessing8_variables _ompWorkSharedUserCode_BitmapProcessing8_variables_instance = null;//####[614]####
//####[615]####
    private static void _ompWorkSharedUserCode_BitmapProcessing8(_ompWorkSharedUserCode_BitmapProcessing8_variables __omp_vars) {//####[615]####
        int index = __omp_vars.index;//####[617]####
        int height = __omp_vars.height;//####[618]####
        Bitmap source = __omp_vars.source;//####[619]####
        int COLOR_MAX = __omp_vars.COLOR_MAX;//####[620]####
        int width = __omp_vars.width;//####[621]####
        Random random = __omp_vars.random;//####[622]####
        int[] pixels = __omp_vars.pixels;//####[623]####
        Integer y;//####[624]####
        while (_pi_8.hasNext()) //####[625]####
        {//####[625]####
            y = _pi_8.next();//####[626]####
            {//####[628]####
                for (int x = 0; x < width; ++x) //####[629]####
                {//####[629]####
                    index = y * width + x;//####[630]####
                    int randColor = Color.rgb(random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));//####[631]####
                    pixels[index] |= randColor;//####[632]####
                }//####[633]####
            }//####[634]####
        }//####[635]####
        __omp_vars.index = index;//####[637]####
        __omp_vars.height = height;//####[638]####
        __omp_vars.source = source;//####[639]####
        __omp_vars.COLOR_MAX = COLOR_MAX;//####[640]####
        __omp_vars.width = width;//####[641]####
        __omp_vars.random = random;//####[642]####
        __omp_vars.pixels = pixels;//####[643]####
    }//####[644]####
//####[648]####
    private static volatile Method __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method = null;//####[648]####
    private synchronized static void __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_ensureMethodVarSet() {//####[648]####
        if (__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method == null) {//####[648]####
            try {//####[648]####
                __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_6", new Class[] {//####[648]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing6.class//####[648]####
                });//####[648]####
            } catch (Exception e) {//####[648]####
                e.printStackTrace();//####[648]####
            }//####[648]####
        }//####[648]####
    }//####[648]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(_omp__parallelRegionVarHolderClass_BitmapProcessing6 __omp_vars) {//####[648]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[648]####
        return _ompParallelRegion_6(__omp_vars, new TaskInfo());//####[648]####
    }//####[648]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(_omp__parallelRegionVarHolderClass_BitmapProcessing6 __omp_vars, TaskInfo taskinfo) {//####[648]####
        // ensure Method variable is set//####[648]####
        if (__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method == null) {//####[648]####
            __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_ensureMethodVarSet();//####[648]####
        }//####[648]####
        taskinfo.setParameters(__omp_vars);//####[648]####
        taskinfo.setMethod(__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method);//####[648]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[648]####
    }//####[648]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing6> __omp_vars) {//####[648]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[648]####
        return _ompParallelRegion_6(__omp_vars, new TaskInfo());//####[648]####
    }//####[648]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing6> __omp_vars, TaskInfo taskinfo) {//####[648]####
        // ensure Method variable is set//####[648]####
        if (__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method == null) {//####[648]####
            __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_ensureMethodVarSet();//####[648]####
        }//####[648]####
        taskinfo.setTaskIdArgIndexes(0);//####[648]####
        taskinfo.addDependsOn(__omp_vars);//####[648]####
        taskinfo.setParameters(__omp_vars);//####[648]####
        taskinfo.setMethod(__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method);//####[648]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[648]####
    }//####[648]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing6> __omp_vars) {//####[648]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[648]####
        return _ompParallelRegion_6(__omp_vars, new TaskInfo());//####[648]####
    }//####[648]####
    private static TaskIDGroup<Void> _ompParallelRegion_6(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing6> __omp_vars, TaskInfo taskinfo) {//####[648]####
        // ensure Method variable is set//####[648]####
        if (__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method == null) {//####[648]####
            __pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_ensureMethodVarSet();//####[648]####
        }//####[648]####
        taskinfo.setQueueArgIndexes(0);//####[648]####
        taskinfo.setIsPipeline(true);//####[648]####
        taskinfo.setParameters(__omp_vars);//####[648]####
        taskinfo.setMethod(__pt___ompParallelRegion_6__omp__parallelRegionVarHolderClass_BitmapProcessing6_method);//####[648]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[648]####
    }//####[648]####
    public static void __pt___ompParallelRegion_6(_omp__parallelRegionVarHolderClass_BitmapProcessing6 __omp_vars) {//####[648]####
        int width = __omp_vars.width;//####[650]####
        int height = __omp_vars.height;//####[651]####
        int[] pixels = __omp_vars.pixels;//####[652]####
        int COLOR_MAX = __omp_vars.COLOR_MAX;//####[653]####
        int index = __omp_vars.index;//####[654]####
        Bitmap source = __omp_vars.source;//####[655]####
        Random random = __omp_vars.random;//####[656]####
        {//####[657]####
            if (Pyjama.insideParallelRegion()) //####[658]####
            {//####[658]####
                boolean _omp_imFirst = _imFirst_8.getAndSet(false);//####[660]####
                _holderForPIFirst = _imFirst_8;//####[661]####
                if (_omp_imFirst) //####[662]####
                {//####[662]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing8_variables();//####[663]####
                    int __omp_size_ = 0;//####[664]####
                    for (int y = 0; y < height; y = y + 1) //####[666]####
                    {//####[666]####
                        _lastElement_8 = y;//####[667]####
                        __omp_size_++;//####[668]####
                    }//####[669]####
                    _pi_8 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[670]####
                    _omp_piVarContainer.add(_pi_8);//####[671]####
                    _pi_8.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[672]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.index = index;//####[673]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.height = height;//####[674]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.source = source;//####[675]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.COLOR_MAX = COLOR_MAX;//####[676]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.width = width;//####[677]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.random = random;//####[678]####
                    _ompWorkSharedUserCode_BitmapProcessing8_variables_instance.pixels = pixels;//####[679]####
                    _waitBarrier_8.countDown();//####[680]####
                } else {//####[681]####
                    try {//####[682]####
                        _waitBarrier_8.await();//####[682]####
                    } catch (InterruptedException __omp__ie) {//####[682]####
                        __omp__ie.printStackTrace();//####[682]####
                    }//####[682]####
                }//####[683]####
                _ompWorkSharedUserCode_BitmapProcessing8(_ompWorkSharedUserCode_BitmapProcessing8_variables_instance);//####[684]####
                if (_imFinishedCounter_8.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[685]####
                {//####[685]####
                    _waitBarrierAfter_8.countDown();//####[686]####
                } else {//####[687]####
                    try {//####[688]####
                        _waitBarrierAfter_8.await();//####[689]####
                    } catch (InterruptedException __omp__ie) {//####[690]####
                        __omp__ie.printStackTrace();//####[691]####
                    }//####[692]####
                }//####[693]####
            } else {//####[696]####
                for (int y = 0; y < height; y = y + 1) //####[698]####
                {//####[698]####
                    for (int x = 0; x < __omp_vars.width; ++x) //####[699]####
                    {//####[699]####
                        __omp_vars.index = y * __omp_vars.width + x;//####[700]####
                        int randColor = Color.rgb(__omp_vars.random.nextInt(__omp_vars.COLOR_MAX), __omp_vars.random.nextInt(__omp_vars.COLOR_MAX), __omp_vars.random.nextInt(__omp_vars.COLOR_MAX));//####[701]####
                        __omp_vars.pixels[__omp_vars.index] |= randColor;//####[702]####
                    }//####[703]####
                }//####[704]####
            }//####[705]####
        }//####[707]####
        __omp_vars.index = index;//####[709]####
        __omp_vars.source = source;//####[710]####
        __omp_vars.random = random;//####[711]####
    }//####[712]####
//####[712]####
//####[713]####
    public static Bitmap brightness(Bitmap src, int value) {//####[713]####
        {//####[713]####
            int width = src.getWidth();//####[714]####
            int height = src.getHeight();//####[715]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[716]####
            int A = 0, R = 0, G = 0, B = 0;//####[717]####
            int pixel = 0;//####[718]####
            if (Pyjama.insideParallelRegion()) //####[720]####
            {//####[720]####
                {//####[722]####
                    for (int x = 0; x < width; x = x + 1) //####[723]####
                    {//####[723]####
                        for (int y = 0; y < height; ++y) //####[724]####
                        {//####[724]####
                            pixel = src.getPixel(x, y);//####[725]####
                            A = Color.alpha(pixel);//####[726]####
                            R = Color.red(pixel);//####[727]####
                            G = Color.green(pixel);//####[728]####
                            B = Color.blue(pixel);//####[729]####
                            R += value;//####[730]####
                            if (R > 255) //####[731]####
                            {//####[731]####
                                R = 255;//####[732]####
                            } else if (R < 0) //####[733]####
                            {//####[733]####
                                R = 0;//####[734]####
                            }//####[735]####
                            G += value;//####[736]####
                            if (G > 255) //####[737]####
                            {//####[737]####
                                G = 255;//####[738]####
                            } else if (G < 0) //####[739]####
                            {//####[739]####
                                G = 0;//####[740]####
                            }//####[741]####
                            B += value;//####[742]####
                            if (B > 255) //####[743]####
                            {//####[743]####
                                B = 255;//####[744]####
                            } else if (B < 0) //####[745]####
                            {//####[745]####
                                B = 0;//####[746]####
                            }//####[747]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[748]####
                        }//####[749]####
                    }//####[750]####
                }//####[751]####
            } else {//####[752]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[754]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing9 _omp__parallelRegionVarHolderInstance_9 = new _omp__parallelRegionVarHolderClass_BitmapProcessing9();//####[757]####
                _omp__parallelRegionVarHolderInstance_9.bmOut = bmOut;//####[758]####
                _omp__parallelRegionVarHolderInstance_9.G = G;//####[759]####
                _omp__parallelRegionVarHolderInstance_9.height = height;//####[760]####
                _omp__parallelRegionVarHolderInstance_9.pixel = pixel;//####[761]####
                _omp__parallelRegionVarHolderInstance_9.A = A;//####[762]####
                _omp__parallelRegionVarHolderInstance_9.B = B;//####[763]####
                _omp__parallelRegionVarHolderInstance_9.R = R;//####[764]####
                _omp__parallelRegionVarHolderInstance_9.width = width;//####[765]####
                _omp__parallelRegionVarHolderInstance_9.value = value;//####[766]####
                _omp__parallelRegionVarHolderInstance_9.src = src;//####[767]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[770]####
                TaskID _omp__parallelRegionTaskID_9 = _ompParallelRegion_9(_omp__parallelRegionVarHolderInstance_9);//####[771]####
                __pt___ompParallelRegion_9(_omp__parallelRegionVarHolderInstance_9);//####[772]####
                try {//####[773]####
                    _omp__parallelRegionTaskID_9.waitTillFinished();//####[773]####
                } catch (Exception __pt__ex) {//####[773]####
                    __pt__ex.printStackTrace();//####[773]####
                }//####[773]####
                PJPackageOnly.setMasterThread(null);//####[775]####
                _holderForPIFirst.set(true);//####[776]####
                bmOut = _omp__parallelRegionVarHolderInstance_9.bmOut;//####[778]####
                G = _omp__parallelRegionVarHolderInstance_9.G;//####[779]####
                height = _omp__parallelRegionVarHolderInstance_9.height;//####[780]####
                pixel = _omp__parallelRegionVarHolderInstance_9.pixel;//####[781]####
                A = _omp__parallelRegionVarHolderInstance_9.A;//####[782]####
                B = _omp__parallelRegionVarHolderInstance_9.B;//####[783]####
                R = _omp__parallelRegionVarHolderInstance_9.R;//####[784]####
                width = _omp__parallelRegionVarHolderInstance_9.width;//####[785]####
                value = _omp__parallelRegionVarHolderInstance_9.value;//####[786]####
                src = _omp__parallelRegionVarHolderInstance_9.src;//####[787]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[788]####
            }//####[789]####
            src.recycle();//####[792]####
            src = null;//####[793]####
            return bmOut;//####[794]####
        }//####[795]####
    }//####[796]####
//####[797]####
    private static AtomicBoolean _imFirst_11 = new AtomicBoolean(true);//####[797]####
//####[798]####
    private static AtomicInteger _imFinishedCounter_11 = new AtomicInteger(0);//####[798]####
//####[799]####
    private static CountDownLatch _waitBarrier_11 = new CountDownLatch(1);//####[799]####
//####[800]####
    private static CountDownLatch _waitBarrierAfter_11 = new CountDownLatch(1);//####[800]####
//####[801]####
    private static ParIterator<Integer> _pi_11 = null;//####[801]####
//####[802]####
    private static Integer _lastElement_11 = null;//####[802]####
//####[803]####
    private static _ompWorkSharedUserCode_BitmapProcessing11_variables _ompWorkSharedUserCode_BitmapProcessing11_variables_instance = null;//####[803]####
//####[804]####
    private static void _ompWorkSharedUserCode_BitmapProcessing11(_ompWorkSharedUserCode_BitmapProcessing11_variables __omp_vars) {//####[804]####
        int width = __omp_vars.width;//####[806]####
        int height = __omp_vars.height;//####[807]####
        int A = __omp_vars.A;//####[808]####
        int R = __omp_vars.R;//####[809]####
        int G = __omp_vars.G;//####[810]####
        int B = __omp_vars.B;//####[811]####
        int pixel = __omp_vars.pixel;//####[812]####
        Bitmap bmOut = __omp_vars.bmOut;//####[813]####
        int value = __omp_vars.value;//####[814]####
        Bitmap src = __omp_vars.src;//####[815]####
        Integer x;//####[816]####
        while (_pi_11.hasNext()) //####[817]####
        {//####[817]####
            x = _pi_11.next();//####[818]####
            {//####[820]####
                for (int y = 0; y < height; ++y) //####[821]####
                {//####[821]####
                    pixel = src.getPixel(x, y);//####[822]####
                    A = Color.alpha(pixel);//####[823]####
                    R = Color.red(pixel);//####[824]####
                    G = Color.green(pixel);//####[825]####
                    B = Color.blue(pixel);//####[826]####
                    R += value;//####[827]####
                    if (R > 255) //####[828]####
                    {//####[828]####
                        R = 255;//####[829]####
                    } else if (R < 0) //####[830]####
                    {//####[830]####
                        R = 0;//####[831]####
                    }//####[832]####
                    G += value;//####[833]####
                    if (G > 255) //####[834]####
                    {//####[834]####
                        G = 255;//####[835]####
                    } else if (G < 0) //####[836]####
                    {//####[836]####
                        G = 0;//####[837]####
                    }//####[838]####
                    B += value;//####[839]####
                    if (B > 255) //####[840]####
                    {//####[840]####
                        B = 255;//####[841]####
                    } else if (B < 0) //####[842]####
                    {//####[842]####
                        B = 0;//####[843]####
                    }//####[844]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[845]####
                }//####[846]####
            }//####[847]####
        }//####[848]####
        __omp_vars.value = value;//####[850]####
        __omp_vars.src = src;//####[851]####
    }//####[852]####
//####[856]####
    private static volatile Method __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method = null;//####[856]####
    private synchronized static void __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_ensureMethodVarSet() {//####[856]####
        if (__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method == null) {//####[856]####
            try {//####[856]####
                __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_9", new Class[] {//####[856]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing9.class//####[856]####
                });//####[856]####
            } catch (Exception e) {//####[856]####
                e.printStackTrace();//####[856]####
            }//####[856]####
        }//####[856]####
    }//####[856]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(_omp__parallelRegionVarHolderClass_BitmapProcessing9 __omp_vars) {//####[856]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[856]####
        return _ompParallelRegion_9(__omp_vars, new TaskInfo());//####[856]####
    }//####[856]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(_omp__parallelRegionVarHolderClass_BitmapProcessing9 __omp_vars, TaskInfo taskinfo) {//####[856]####
        // ensure Method variable is set//####[856]####
        if (__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method == null) {//####[856]####
            __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_ensureMethodVarSet();//####[856]####
        }//####[856]####
        taskinfo.setParameters(__omp_vars);//####[856]####
        taskinfo.setMethod(__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method);//####[856]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[856]####
    }//####[856]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing9> __omp_vars) {//####[856]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[856]####
        return _ompParallelRegion_9(__omp_vars, new TaskInfo());//####[856]####
    }//####[856]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing9> __omp_vars, TaskInfo taskinfo) {//####[856]####
        // ensure Method variable is set//####[856]####
        if (__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method == null) {//####[856]####
            __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_ensureMethodVarSet();//####[856]####
        }//####[856]####
        taskinfo.setTaskIdArgIndexes(0);//####[856]####
        taskinfo.addDependsOn(__omp_vars);//####[856]####
        taskinfo.setParameters(__omp_vars);//####[856]####
        taskinfo.setMethod(__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method);//####[856]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[856]####
    }//####[856]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing9> __omp_vars) {//####[856]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[856]####
        return _ompParallelRegion_9(__omp_vars, new TaskInfo());//####[856]####
    }//####[856]####
    private static TaskIDGroup<Void> _ompParallelRegion_9(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing9> __omp_vars, TaskInfo taskinfo) {//####[856]####
        // ensure Method variable is set//####[856]####
        if (__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method == null) {//####[856]####
            __pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_ensureMethodVarSet();//####[856]####
        }//####[856]####
        taskinfo.setQueueArgIndexes(0);//####[856]####
        taskinfo.setIsPipeline(true);//####[856]####
        taskinfo.setParameters(__omp_vars);//####[856]####
        taskinfo.setMethod(__pt___ompParallelRegion_9__omp__parallelRegionVarHolderClass_BitmapProcessing9_method);//####[856]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[856]####
    }//####[856]####
    public static void __pt___ompParallelRegion_9(_omp__parallelRegionVarHolderClass_BitmapProcessing9 __omp_vars) {//####[856]####
        Bitmap bmOut = __omp_vars.bmOut;//####[858]####
        int G = __omp_vars.G;//####[859]####
        int height = __omp_vars.height;//####[860]####
        int pixel = __omp_vars.pixel;//####[861]####
        int A = __omp_vars.A;//####[862]####
        int B = __omp_vars.B;//####[863]####
        int R = __omp_vars.R;//####[864]####
        int width = __omp_vars.width;//####[865]####
        int value = __omp_vars.value;//####[866]####
        Bitmap src = __omp_vars.src;//####[867]####
        {//####[868]####
            if (Pyjama.insideParallelRegion()) //####[869]####
            {//####[869]####
                boolean _omp_imFirst = _imFirst_11.getAndSet(false);//####[871]####
                _holderForPIFirst = _imFirst_11;//####[872]####
                if (_omp_imFirst) //####[873]####
                {//####[873]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing11_variables();//####[874]####
                    int __omp_size_ = 0;//####[875]####
                    for (int x = 0; x < width; x = x + 1) //####[877]####
                    {//####[877]####
                        _lastElement_11 = x;//####[878]####
                        __omp_size_++;//####[879]####
                    }//####[880]####
                    _pi_11 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[881]####
                    _omp_piVarContainer.add(_pi_11);//####[882]####
                    _pi_11.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[883]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.width = width;//####[885]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.height = height;//####[886]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.A = A;//####[887]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.R = R;//####[888]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.G = G;//####[889]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.B = B;//####[890]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.pixel = pixel;//####[891]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.bmOut = bmOut;//####[892]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.value = value;//####[893]####
                    _ompWorkSharedUserCode_BitmapProcessing11_variables_instance.src = src;//####[894]####
                    _waitBarrier_11.countDown();//####[895]####
                } else {//####[896]####
                    try {//####[897]####
                        _waitBarrier_11.await();//####[897]####
                    } catch (InterruptedException __omp__ie) {//####[897]####
                        __omp__ie.printStackTrace();//####[897]####
                    }//####[897]####
                }//####[898]####
                _ompWorkSharedUserCode_BitmapProcessing11(_ompWorkSharedUserCode_BitmapProcessing11_variables_instance);//####[899]####
                if (_imFinishedCounter_11.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[900]####
                {//####[900]####
                    _waitBarrierAfter_11.countDown();//####[901]####
                } else {//####[902]####
                    try {//####[903]####
                        _waitBarrierAfter_11.await();//####[904]####
                    } catch (InterruptedException __omp__ie) {//####[905]####
                        __omp__ie.printStackTrace();//####[906]####
                    }//####[907]####
                }//####[908]####
            } else {//####[911]####
                for (int x = 0; x < width; x = x + 1) //####[913]####
                {//####[913]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[914]####
                    {//####[914]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[915]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[916]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[917]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[918]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[919]####
                        __omp_vars.R += __omp_vars.value;//####[920]####
                        if (__omp_vars.R > 255) //####[921]####
                        {//####[921]####
                            __omp_vars.R = 255;//####[922]####
                        } else if (__omp_vars.R < 0) //####[923]####
                        {//####[923]####
                            __omp_vars.R = 0;//####[924]####
                        }//####[925]####
                        __omp_vars.G += __omp_vars.value;//####[926]####
                        if (__omp_vars.G > 255) //####[927]####
                        {//####[927]####
                            __omp_vars.G = 255;//####[928]####
                        } else if (__omp_vars.G < 0) //####[929]####
                        {//####[929]####
                            __omp_vars.G = 0;//####[930]####
                        }//####[931]####
                        __omp_vars.B += __omp_vars.value;//####[932]####
                        if (__omp_vars.B > 255) //####[933]####
                        {//####[933]####
                            __omp_vars.B = 255;//####[934]####
                        } else if (__omp_vars.B < 0) //####[935]####
                        {//####[935]####
                            __omp_vars.B = 0;//####[936]####
                        }//####[937]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[938]####
                    }//####[939]####
                }//####[940]####
            }//####[941]####
        }//####[943]####
        __omp_vars.bmOut = bmOut;//####[945]####
        __omp_vars.G = G;//####[946]####
        __omp_vars.height = height;//####[947]####
        __omp_vars.pixel = pixel;//####[948]####
        __omp_vars.A = A;//####[949]####
        __omp_vars.B = B;//####[950]####
        __omp_vars.R = R;//####[951]####
        __omp_vars.width = width;//####[952]####
        __omp_vars.value = value;//####[953]####
        __omp_vars.src = src;//####[954]####
    }//####[955]####
//####[955]####
//####[956]####
    public static Bitmap sepia(Bitmap src) {//####[956]####
        {//####[956]####
            int width = src.getWidth();//####[957]####
            int height = src.getHeight();//####[958]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[959]####
            double GS_RED = 0.3;//####[960]####
            double GS_GREEN = 0.59;//####[961]####
            double GS_BLUE = 0.11;//####[962]####
            int A = 0, R = 0, G = 0, B = 0;//####[963]####
            int pixel = 0;//####[964]####
            if (Pyjama.insideParallelRegion()) //####[966]####
            {//####[966]####
                {//####[968]####
                    for (int x = 0; x < width; x = x + 1) //####[969]####
                    {//####[969]####
                        for (int y = 0; y < height; ++y) //####[970]####
                        {//####[970]####
                            pixel = src.getPixel(x, y);//####[971]####
                            A = Color.alpha(pixel);//####[972]####
                            R = Color.red(pixel);//####[973]####
                            G = Color.green(pixel);//####[974]####
                            B = Color.blue(pixel);//####[975]####
                            B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);//####[976]####
                            R += 110;//####[977]####
                            if (R > 255) //####[978]####
                            {//####[978]####
                                R = 255;//####[979]####
                            }//####[980]####
                            G += 65;//####[981]####
                            if (G > 255) //####[982]####
                            {//####[982]####
                                G = 255;//####[983]####
                            }//####[984]####
                            B += 20;//####[985]####
                            if (B > 255) //####[986]####
                            {//####[986]####
                                B = 255;//####[987]####
                            }//####[988]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[989]####
                        }//####[990]####
                    }//####[991]####
                }//####[992]####
            } else {//####[993]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[995]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing12 _omp__parallelRegionVarHolderInstance_12 = new _omp__parallelRegionVarHolderClass_BitmapProcessing12();//####[998]####
                _omp__parallelRegionVarHolderInstance_12.width = width;//####[1001]####
                _omp__parallelRegionVarHolderInstance_12.height = height;//####[1002]####
                _omp__parallelRegionVarHolderInstance_12.A = A;//####[1003]####
                _omp__parallelRegionVarHolderInstance_12.R = R;//####[1004]####
                _omp__parallelRegionVarHolderInstance_12.G = G;//####[1005]####
                _omp__parallelRegionVarHolderInstance_12.B = B;//####[1006]####
                _omp__parallelRegionVarHolderInstance_12.pixel = pixel;//####[1007]####
                _omp__parallelRegionVarHolderInstance_12.bmOut = bmOut;//####[1008]####
                _omp__parallelRegionVarHolderInstance_12.GS_GREEN = GS_GREEN;//####[1009]####
                _omp__parallelRegionVarHolderInstance_12.GS_BLUE = GS_BLUE;//####[1010]####
                _omp__parallelRegionVarHolderInstance_12.src = src;//####[1011]####
                _omp__parallelRegionVarHolderInstance_12.GS_RED = GS_RED;//####[1012]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[1015]####
                TaskID _omp__parallelRegionTaskID_12 = _ompParallelRegion_12(_omp__parallelRegionVarHolderInstance_12);//####[1016]####
                __pt___ompParallelRegion_12(_omp__parallelRegionVarHolderInstance_12);//####[1017]####
                try {//####[1018]####
                    _omp__parallelRegionTaskID_12.waitTillFinished();//####[1018]####
                } catch (Exception __pt__ex) {//####[1018]####
                    __pt__ex.printStackTrace();//####[1018]####
                }//####[1018]####
                PJPackageOnly.setMasterThread(null);//####[1020]####
                _holderForPIFirst.set(true);//####[1021]####
                width = _omp__parallelRegionVarHolderInstance_12.width;//####[1023]####
                height = _omp__parallelRegionVarHolderInstance_12.height;//####[1024]####
                A = _omp__parallelRegionVarHolderInstance_12.A;//####[1025]####
                R = _omp__parallelRegionVarHolderInstance_12.R;//####[1026]####
                G = _omp__parallelRegionVarHolderInstance_12.G;//####[1027]####
                B = _omp__parallelRegionVarHolderInstance_12.B;//####[1028]####
                pixel = _omp__parallelRegionVarHolderInstance_12.pixel;//####[1029]####
                bmOut = _omp__parallelRegionVarHolderInstance_12.bmOut;//####[1030]####
                GS_GREEN = _omp__parallelRegionVarHolderInstance_12.GS_GREEN;//####[1031]####
                GS_BLUE = _omp__parallelRegionVarHolderInstance_12.GS_BLUE;//####[1032]####
                src = _omp__parallelRegionVarHolderInstance_12.src;//####[1033]####
                GS_RED = _omp__parallelRegionVarHolderInstance_12.GS_RED;//####[1034]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[1035]####
            }//####[1036]####
            src.recycle();//####[1039]####
            src = null;//####[1040]####
            return bmOut;//####[1041]####
        }//####[1042]####
    }//####[1043]####
//####[1044]####
    private static AtomicBoolean _imFirst_14 = new AtomicBoolean(true);//####[1044]####
//####[1045]####
    private static AtomicInteger _imFinishedCounter_14 = new AtomicInteger(0);//####[1045]####
//####[1046]####
    private static CountDownLatch _waitBarrier_14 = new CountDownLatch(1);//####[1046]####
//####[1047]####
    private static CountDownLatch _waitBarrierAfter_14 = new CountDownLatch(1);//####[1047]####
//####[1048]####
    private static ParIterator<Integer> _pi_14 = null;//####[1048]####
//####[1049]####
    private static Integer _lastElement_14 = null;//####[1049]####
//####[1050]####
    private static _ompWorkSharedUserCode_BitmapProcessing14_variables _ompWorkSharedUserCode_BitmapProcessing14_variables_instance = null;//####[1050]####
//####[1051]####
    private static void _ompWorkSharedUserCode_BitmapProcessing14(_ompWorkSharedUserCode_BitmapProcessing14_variables __omp_vars) {//####[1051]####
        double GS_GREEN = __omp_vars.GS_GREEN;//####[1053]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1054]####
        int G = __omp_vars.G;//####[1055]####
        int height = __omp_vars.height;//####[1056]####
        int pixel = __omp_vars.pixel;//####[1057]####
        int A = __omp_vars.A;//####[1058]####
        int B = __omp_vars.B;//####[1059]####
        double GS_BLUE = __omp_vars.GS_BLUE;//####[1060]####
        int R = __omp_vars.R;//####[1061]####
        int width = __omp_vars.width;//####[1062]####
        Bitmap src = __omp_vars.src;//####[1063]####
        double GS_RED = __omp_vars.GS_RED;//####[1064]####
        Integer x;//####[1065]####
        while (_pi_14.hasNext()) //####[1066]####
        {//####[1066]####
            x = _pi_14.next();//####[1067]####
            {//####[1069]####
                for (int y = 0; y < height; ++y) //####[1070]####
                {//####[1070]####
                    pixel = src.getPixel(x, y);//####[1071]####
                    A = Color.alpha(pixel);//####[1072]####
                    R = Color.red(pixel);//####[1073]####
                    G = Color.green(pixel);//####[1074]####
                    B = Color.blue(pixel);//####[1075]####
                    B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);//####[1076]####
                    R += 110;//####[1077]####
                    if (R > 255) //####[1078]####
                    {//####[1078]####
                        R = 255;//####[1079]####
                    }//####[1080]####
                    G += 65;//####[1081]####
                    if (G > 255) //####[1082]####
                    {//####[1082]####
                        G = 255;//####[1083]####
                    }//####[1084]####
                    B += 20;//####[1085]####
                    if (B > 255) //####[1086]####
                    {//####[1086]####
                        B = 255;//####[1087]####
                    }//####[1088]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1089]####
                }//####[1090]####
            }//####[1091]####
        }//####[1092]####
        __omp_vars.GS_GREEN = GS_GREEN;//####[1094]####
        __omp_vars.bmOut = bmOut;//####[1095]####
        __omp_vars.G = G;//####[1096]####
        __omp_vars.height = height;//####[1097]####
        __omp_vars.pixel = pixel;//####[1098]####
        __omp_vars.A = A;//####[1099]####
        __omp_vars.B = B;//####[1100]####
        __omp_vars.GS_BLUE = GS_BLUE;//####[1101]####
        __omp_vars.R = R;//####[1102]####
        __omp_vars.width = width;//####[1103]####
        __omp_vars.src = src;//####[1104]####
        __omp_vars.GS_RED = GS_RED;//####[1105]####
    }//####[1106]####
//####[1110]####
    private static volatile Method __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method = null;//####[1110]####
    private synchronized static void __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_ensureMethodVarSet() {//####[1110]####
        if (__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method == null) {//####[1110]####
            try {//####[1110]####
                __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_12", new Class[] {//####[1110]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing12.class//####[1110]####
                });//####[1110]####
            } catch (Exception e) {//####[1110]####
                e.printStackTrace();//####[1110]####
            }//####[1110]####
        }//####[1110]####
    }//####[1110]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(_omp__parallelRegionVarHolderClass_BitmapProcessing12 __omp_vars) {//####[1110]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1110]####
        return _ompParallelRegion_12(__omp_vars, new TaskInfo());//####[1110]####
    }//####[1110]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(_omp__parallelRegionVarHolderClass_BitmapProcessing12 __omp_vars, TaskInfo taskinfo) {//####[1110]####
        // ensure Method variable is set//####[1110]####
        if (__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method == null) {//####[1110]####
            __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_ensureMethodVarSet();//####[1110]####
        }//####[1110]####
        taskinfo.setParameters(__omp_vars);//####[1110]####
        taskinfo.setMethod(__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method);//####[1110]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1110]####
    }//####[1110]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing12> __omp_vars) {//####[1110]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1110]####
        return _ompParallelRegion_12(__omp_vars, new TaskInfo());//####[1110]####
    }//####[1110]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing12> __omp_vars, TaskInfo taskinfo) {//####[1110]####
        // ensure Method variable is set//####[1110]####
        if (__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method == null) {//####[1110]####
            __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_ensureMethodVarSet();//####[1110]####
        }//####[1110]####
        taskinfo.setTaskIdArgIndexes(0);//####[1110]####
        taskinfo.addDependsOn(__omp_vars);//####[1110]####
        taskinfo.setParameters(__omp_vars);//####[1110]####
        taskinfo.setMethod(__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method);//####[1110]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1110]####
    }//####[1110]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing12> __omp_vars) {//####[1110]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1110]####
        return _ompParallelRegion_12(__omp_vars, new TaskInfo());//####[1110]####
    }//####[1110]####
    private static TaskIDGroup<Void> _ompParallelRegion_12(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing12> __omp_vars, TaskInfo taskinfo) {//####[1110]####
        // ensure Method variable is set//####[1110]####
        if (__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method == null) {//####[1110]####
            __pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_ensureMethodVarSet();//####[1110]####
        }//####[1110]####
        taskinfo.setQueueArgIndexes(0);//####[1110]####
        taskinfo.setIsPipeline(true);//####[1110]####
        taskinfo.setParameters(__omp_vars);//####[1110]####
        taskinfo.setMethod(__pt___ompParallelRegion_12__omp__parallelRegionVarHolderClass_BitmapProcessing12_method);//####[1110]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1110]####
    }//####[1110]####
    public static void __pt___ompParallelRegion_12(_omp__parallelRegionVarHolderClass_BitmapProcessing12 __omp_vars) {//####[1110]####
        int width = __omp_vars.width;//####[1112]####
        int height = __omp_vars.height;//####[1113]####
        int A = __omp_vars.A;//####[1114]####
        int R = __omp_vars.R;//####[1115]####
        int G = __omp_vars.G;//####[1116]####
        int B = __omp_vars.B;//####[1117]####
        int pixel = __omp_vars.pixel;//####[1118]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1119]####
        double GS_GREEN = __omp_vars.GS_GREEN;//####[1120]####
        double GS_BLUE = __omp_vars.GS_BLUE;//####[1121]####
        Bitmap src = __omp_vars.src;//####[1122]####
        double GS_RED = __omp_vars.GS_RED;//####[1123]####
        {//####[1124]####
            if (Pyjama.insideParallelRegion()) //####[1125]####
            {//####[1125]####
                boolean _omp_imFirst = _imFirst_14.getAndSet(false);//####[1127]####
                _holderForPIFirst = _imFirst_14;//####[1128]####
                if (_omp_imFirst) //####[1129]####
                {//####[1129]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing14_variables();//####[1130]####
                    int __omp_size_ = 0;//####[1131]####
                    for (int x = 0; x < width; x = x + 1) //####[1133]####
                    {//####[1133]####
                        _lastElement_14 = x;//####[1134]####
                        __omp_size_++;//####[1135]####
                    }//####[1136]####
                    _pi_14 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[1137]####
                    _omp_piVarContainer.add(_pi_14);//####[1138]####
                    _pi_14.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[1139]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.GS_GREEN = GS_GREEN;//####[1140]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.bmOut = bmOut;//####[1141]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.G = G;//####[1142]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.height = height;//####[1143]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.pixel = pixel;//####[1144]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.A = A;//####[1145]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.B = B;//####[1146]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.GS_BLUE = GS_BLUE;//####[1147]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.R = R;//####[1148]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.width = width;//####[1149]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.src = src;//####[1150]####
                    _ompWorkSharedUserCode_BitmapProcessing14_variables_instance.GS_RED = GS_RED;//####[1151]####
                    _waitBarrier_14.countDown();//####[1152]####
                } else {//####[1153]####
                    try {//####[1154]####
                        _waitBarrier_14.await();//####[1154]####
                    } catch (InterruptedException __omp__ie) {//####[1154]####
                        __omp__ie.printStackTrace();//####[1154]####
                    }//####[1154]####
                }//####[1155]####
                _ompWorkSharedUserCode_BitmapProcessing14(_ompWorkSharedUserCode_BitmapProcessing14_variables_instance);//####[1156]####
                if (_imFinishedCounter_14.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[1157]####
                {//####[1157]####
                    _waitBarrierAfter_14.countDown();//####[1158]####
                } else {//####[1159]####
                    try {//####[1160]####
                        _waitBarrierAfter_14.await();//####[1161]####
                    } catch (InterruptedException __omp__ie) {//####[1162]####
                        __omp__ie.printStackTrace();//####[1163]####
                    }//####[1164]####
                }//####[1165]####
            } else {//####[1168]####
                for (int x = 0; x < width; x = x + 1) //####[1170]####
                {//####[1170]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[1171]####
                    {//####[1171]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[1172]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[1173]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[1174]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[1175]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[1176]####
                        __omp_vars.B = __omp_vars.G = __omp_vars.R = (int) (__omp_vars.GS_RED * __omp_vars.R + __omp_vars.GS_GREEN * __omp_vars.G + __omp_vars.GS_BLUE * __omp_vars.B);//####[1177]####
                        __omp_vars.R += 110;//####[1178]####
                        if (__omp_vars.R > 255) //####[1179]####
                        {//####[1179]####
                            __omp_vars.R = 255;//####[1180]####
                        }//####[1181]####
                        __omp_vars.G += 65;//####[1182]####
                        if (__omp_vars.G > 255) //####[1183]####
                        {//####[1183]####
                            __omp_vars.G = 255;//####[1184]####
                        }//####[1185]####
                        __omp_vars.B += 20;//####[1186]####
                        if (__omp_vars.B > 255) //####[1187]####
                        {//####[1187]####
                            __omp_vars.B = 255;//####[1188]####
                        }//####[1189]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[1190]####
                    }//####[1191]####
                }//####[1192]####
            }//####[1193]####
        }//####[1195]####
        __omp_vars.GS_GREEN = GS_GREEN;//####[1197]####
        __omp_vars.GS_BLUE = GS_BLUE;//####[1198]####
        __omp_vars.src = src;//####[1199]####
        __omp_vars.GS_RED = GS_RED;//####[1200]####
    }//####[1201]####
//####[1201]####
//####[1202]####
    public static Bitmap gamma(Bitmap src, double red, double green, double blue) {//####[1202]####
        {//####[1202]####
            red = (double) (red + 2) / 10.0;//####[1203]####
            green = (double) (green + 2) / 10.0;//####[1204]####
            blue = (double) (blue + 2) / 10.0;//####[1205]####
            Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());//####[1206]####
            int width = src.getWidth();//####[1207]####
            int height = src.getHeight();//####[1208]####
            int A = 0, R = 0, G = 0, B = 0;//####[1209]####
            int pixel = 0;//####[1210]####
            int MAX_SIZE = 256;//####[1211]####
            double MAX_VALUE_DBL = 255.0;//####[1212]####
            int MAX_VALUE_INT = 255;//####[1213]####
            double REVERSE = 1.0;//####[1214]####
            int[] gammaR = new int[MAX_SIZE];//####[1215]####
            int[] gammaG = new int[MAX_SIZE];//####[1216]####
            int[] gammaB = new int[MAX_SIZE];//####[1217]####
            if (Pyjama.insideParallelRegion()) //####[1219]####
            {//####[1219]####
                {//####[1221]####
                    for (int i = 0; i < MAX_SIZE; i = i + 1) //####[1222]####
                    {//####[1222]####
                        gammaR[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));//####[1223]####
                        gammaG[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));//####[1224]####
                        gammaB[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));//####[1225]####
                    }//####[1226]####
                }//####[1227]####
            } else {//####[1228]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[1230]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing15 _omp__parallelRegionVarHolderInstance_15 = new _omp__parallelRegionVarHolderClass_BitmapProcessing15();//####[1233]####
                _omp__parallelRegionVarHolderInstance_15.MAX_VALUE_INT = MAX_VALUE_INT;//####[1234]####
                _omp__parallelRegionVarHolderInstance_15.G = G;//####[1235]####
                _omp__parallelRegionVarHolderInstance_15.pixel = pixel;//####[1236]####
                _omp__parallelRegionVarHolderInstance_15.A = A;//####[1237]####
                _omp__parallelRegionVarHolderInstance_15.green = green;//####[1238]####
                _omp__parallelRegionVarHolderInstance_15.MAX_SIZE = MAX_SIZE;//####[1239]####
                _omp__parallelRegionVarHolderInstance_15.B = B;//####[1240]####
                _omp__parallelRegionVarHolderInstance_15.width = width;//####[1241]####
                _omp__parallelRegionVarHolderInstance_15.gammaB = gammaB;//####[1242]####
                _omp__parallelRegionVarHolderInstance_15.bmOut = bmOut;//####[1243]####
                _omp__parallelRegionVarHolderInstance_15.red = red;//####[1244]####
                _omp__parallelRegionVarHolderInstance_15.height = height;//####[1245]####
                _omp__parallelRegionVarHolderInstance_15.blue = blue;//####[1246]####
                _omp__parallelRegionVarHolderInstance_15.gammaR = gammaR;//####[1247]####
                _omp__parallelRegionVarHolderInstance_15.R = R;//####[1248]####
                _omp__parallelRegionVarHolderInstance_15.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1249]####
                _omp__parallelRegionVarHolderInstance_15.REVERSE = REVERSE;//####[1250]####
                _omp__parallelRegionVarHolderInstance_15.src = src;//####[1251]####
                _omp__parallelRegionVarHolderInstance_15.gammaG = gammaG;//####[1252]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[1255]####
                TaskID _omp__parallelRegionTaskID_15 = _ompParallelRegion_15(_omp__parallelRegionVarHolderInstance_15);//####[1256]####
                __pt___ompParallelRegion_15(_omp__parallelRegionVarHolderInstance_15);//####[1257]####
                try {//####[1258]####
                    _omp__parallelRegionTaskID_15.waitTillFinished();//####[1258]####
                } catch (Exception __pt__ex) {//####[1258]####
                    __pt__ex.printStackTrace();//####[1258]####
                }//####[1258]####
                PJPackageOnly.setMasterThread(null);//####[1260]####
                _holderForPIFirst.set(true);//####[1261]####
                MAX_VALUE_INT = _omp__parallelRegionVarHolderInstance_15.MAX_VALUE_INT;//####[1263]####
                G = _omp__parallelRegionVarHolderInstance_15.G;//####[1264]####
                pixel = _omp__parallelRegionVarHolderInstance_15.pixel;//####[1265]####
                A = _omp__parallelRegionVarHolderInstance_15.A;//####[1266]####
                green = _omp__parallelRegionVarHolderInstance_15.green;//####[1267]####
                MAX_SIZE = _omp__parallelRegionVarHolderInstance_15.MAX_SIZE;//####[1268]####
                B = _omp__parallelRegionVarHolderInstance_15.B;//####[1269]####
                width = _omp__parallelRegionVarHolderInstance_15.width;//####[1270]####
                gammaB = _omp__parallelRegionVarHolderInstance_15.gammaB;//####[1271]####
                bmOut = _omp__parallelRegionVarHolderInstance_15.bmOut;//####[1272]####
                red = _omp__parallelRegionVarHolderInstance_15.red;//####[1273]####
                height = _omp__parallelRegionVarHolderInstance_15.height;//####[1274]####
                blue = _omp__parallelRegionVarHolderInstance_15.blue;//####[1275]####
                gammaR = _omp__parallelRegionVarHolderInstance_15.gammaR;//####[1276]####
                R = _omp__parallelRegionVarHolderInstance_15.R;//####[1277]####
                MAX_VALUE_DBL = _omp__parallelRegionVarHolderInstance_15.MAX_VALUE_DBL;//####[1278]####
                REVERSE = _omp__parallelRegionVarHolderInstance_15.REVERSE;//####[1279]####
                src = _omp__parallelRegionVarHolderInstance_15.src;//####[1280]####
                gammaG = _omp__parallelRegionVarHolderInstance_15.gammaG;//####[1281]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[1282]####
            }//####[1283]####
            if (Pyjama.insideParallelRegion()) //####[1287]####
            {//####[1287]####
                {//####[1289]####
                    for (int x = 0; x < width; x = x + 1) //####[1290]####
                    {//####[1290]####
                        for (int y = 0; y < height; y++) //####[1291]####
                        {//####[1291]####
                            pixel = src.getPixel(x, y);//####[1292]####
                            A = Color.alpha(pixel);//####[1293]####
                            R = gammaR[Color.red(pixel)];//####[1294]####
                            G = gammaG[Color.green(pixel)];//####[1295]####
                            B = gammaB[Color.blue(pixel)];//####[1296]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1297]####
                        }//####[1298]####
                    }//####[1299]####
                }//####[1300]####
            } else {//####[1301]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[1303]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing18 _omp__parallelRegionVarHolderInstance_18 = new _omp__parallelRegionVarHolderClass_BitmapProcessing18();//####[1306]####
                _omp__parallelRegionVarHolderInstance_18.G = G;//####[1307]####
                _omp__parallelRegionVarHolderInstance_18.MAX_VALUE_INT = MAX_VALUE_INT;//####[1308]####
                _omp__parallelRegionVarHolderInstance_18.A = A;//####[1309]####
                _omp__parallelRegionVarHolderInstance_18.pixel = pixel;//####[1310]####
                _omp__parallelRegionVarHolderInstance_18.B = B;//####[1311]####
                _omp__parallelRegionVarHolderInstance_18.MAX_SIZE = MAX_SIZE;//####[1312]####
                _omp__parallelRegionVarHolderInstance_18.green = green;//####[1313]####
                _omp__parallelRegionVarHolderInstance_18.width = width;//####[1314]####
                _omp__parallelRegionVarHolderInstance_18.gammaB = gammaB;//####[1315]####
                _omp__parallelRegionVarHolderInstance_18.bmOut = bmOut;//####[1316]####
                _omp__parallelRegionVarHolderInstance_18.height = height;//####[1317]####
                _omp__parallelRegionVarHolderInstance_18.red = red;//####[1318]####
                _omp__parallelRegionVarHolderInstance_18.blue = blue;//####[1319]####
                _omp__parallelRegionVarHolderInstance_18.gammaR = gammaR;//####[1320]####
                _omp__parallelRegionVarHolderInstance_18.R = R;//####[1321]####
                _omp__parallelRegionVarHolderInstance_18.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1322]####
                _omp__parallelRegionVarHolderInstance_18.REVERSE = REVERSE;//####[1323]####
                _omp__parallelRegionVarHolderInstance_18.src = src;//####[1324]####
                _omp__parallelRegionVarHolderInstance_18.gammaG = gammaG;//####[1325]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[1328]####
                TaskID _omp__parallelRegionTaskID_18 = _ompParallelRegion_18(_omp__parallelRegionVarHolderInstance_18);//####[1329]####
                __pt___ompParallelRegion_18(_omp__parallelRegionVarHolderInstance_18);//####[1330]####
                try {//####[1331]####
                    _omp__parallelRegionTaskID_18.waitTillFinished();//####[1331]####
                } catch (Exception __pt__ex) {//####[1331]####
                    __pt__ex.printStackTrace();//####[1331]####
                }//####[1331]####
                PJPackageOnly.setMasterThread(null);//####[1333]####
                _holderForPIFirst.set(true);//####[1334]####
                G = _omp__parallelRegionVarHolderInstance_18.G;//####[1336]####
                MAX_VALUE_INT = _omp__parallelRegionVarHolderInstance_18.MAX_VALUE_INT;//####[1337]####
                A = _omp__parallelRegionVarHolderInstance_18.A;//####[1338]####
                pixel = _omp__parallelRegionVarHolderInstance_18.pixel;//####[1339]####
                B = _omp__parallelRegionVarHolderInstance_18.B;//####[1340]####
                MAX_SIZE = _omp__parallelRegionVarHolderInstance_18.MAX_SIZE;//####[1341]####
                green = _omp__parallelRegionVarHolderInstance_18.green;//####[1342]####
                width = _omp__parallelRegionVarHolderInstance_18.width;//####[1343]####
                gammaB = _omp__parallelRegionVarHolderInstance_18.gammaB;//####[1344]####
                bmOut = _omp__parallelRegionVarHolderInstance_18.bmOut;//####[1345]####
                height = _omp__parallelRegionVarHolderInstance_18.height;//####[1346]####
                red = _omp__parallelRegionVarHolderInstance_18.red;//####[1347]####
                blue = _omp__parallelRegionVarHolderInstance_18.blue;//####[1348]####
                gammaR = _omp__parallelRegionVarHolderInstance_18.gammaR;//####[1349]####
                R = _omp__parallelRegionVarHolderInstance_18.R;//####[1350]####
                MAX_VALUE_DBL = _omp__parallelRegionVarHolderInstance_18.MAX_VALUE_DBL;//####[1351]####
                REVERSE = _omp__parallelRegionVarHolderInstance_18.REVERSE;//####[1352]####
                src = _omp__parallelRegionVarHolderInstance_18.src;//####[1353]####
                gammaG = _omp__parallelRegionVarHolderInstance_18.gammaG;//####[1354]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[1355]####
            }//####[1356]####
            src.recycle();//####[1359]####
            src = null;//####[1360]####
            return bmOut;//####[1361]####
        }//####[1362]####
    }//####[1363]####
//####[1364]####
    private static AtomicBoolean _imFirst_17 = new AtomicBoolean(true);//####[1364]####
//####[1365]####
    private static AtomicInteger _imFinishedCounter_17 = new AtomicInteger(0);//####[1365]####
//####[1366]####
    private static CountDownLatch _waitBarrier_17 = new CountDownLatch(1);//####[1366]####
//####[1367]####
    private static CountDownLatch _waitBarrierAfter_17 = new CountDownLatch(1);//####[1367]####
//####[1368]####
    private static ParIterator<Integer> _pi_17 = null;//####[1368]####
//####[1369]####
    private static Integer _lastElement_17 = null;//####[1369]####
//####[1370]####
    private static _ompWorkSharedUserCode_BitmapProcessing17_variables _ompWorkSharedUserCode_BitmapProcessing17_variables_instance = null;//####[1370]####
//####[1371]####
    private static void _ompWorkSharedUserCode_BitmapProcessing17(_ompWorkSharedUserCode_BitmapProcessing17_variables __omp_vars) {//####[1371]####
        int MAX_SIZE = __omp_vars.MAX_SIZE;//####[1373]####
        int[] gammaR = __omp_vars.gammaR;//####[1374]####
        int[] gammaG = __omp_vars.gammaG;//####[1375]####
        int[] gammaB = __omp_vars.gammaB;//####[1376]####
        int MAX_VALUE_INT = __omp_vars.MAX_VALUE_INT;//####[1377]####
        double MAX_VALUE_DBL = __omp_vars.MAX_VALUE_DBL;//####[1378]####
        double REVERSE = __omp_vars.REVERSE;//####[1379]####
        double red = __omp_vars.red;//####[1380]####
        double green = __omp_vars.green;//####[1381]####
        double blue = __omp_vars.blue;//####[1382]####
        int G = __omp_vars.G;//####[1383]####
        int A = __omp_vars.A;//####[1384]####
        int pixel = __omp_vars.pixel;//####[1385]####
        int B = __omp_vars.B;//####[1386]####
        int width = __omp_vars.width;//####[1387]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1388]####
        int height = __omp_vars.height;//####[1389]####
        int R = __omp_vars.R;//####[1390]####
        Bitmap src = __omp_vars.src;//####[1391]####
        Integer i;//####[1392]####
        while (_pi_17.hasNext()) //####[1393]####
        {//####[1393]####
            i = _pi_17.next();//####[1394]####
            {//####[1396]####
                gammaR[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));//####[1397]####
                gammaG[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));//####[1398]####
                gammaB[i] = (int) Math.min(MAX_VALUE_INT, (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));//####[1399]####
            }//####[1400]####
        }//####[1401]####
        __omp_vars.G = G;//####[1403]####
        __omp_vars.A = A;//####[1404]####
        __omp_vars.pixel = pixel;//####[1405]####
        __omp_vars.B = B;//####[1406]####
        __omp_vars.width = width;//####[1407]####
        __omp_vars.bmOut = bmOut;//####[1408]####
        __omp_vars.height = height;//####[1409]####
        __omp_vars.R = R;//####[1410]####
        __omp_vars.src = src;//####[1411]####
    }//####[1412]####
//####[1416]####
    private static volatile Method __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method = null;//####[1416]####
    private synchronized static void __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_ensureMethodVarSet() {//####[1416]####
        if (__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method == null) {//####[1416]####
            try {//####[1416]####
                __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_15", new Class[] {//####[1416]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing15.class//####[1416]####
                });//####[1416]####
            } catch (Exception e) {//####[1416]####
                e.printStackTrace();//####[1416]####
            }//####[1416]####
        }//####[1416]####
    }//####[1416]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(_omp__parallelRegionVarHolderClass_BitmapProcessing15 __omp_vars) {//####[1416]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1416]####
        return _ompParallelRegion_15(__omp_vars, new TaskInfo());//####[1416]####
    }//####[1416]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(_omp__parallelRegionVarHolderClass_BitmapProcessing15 __omp_vars, TaskInfo taskinfo) {//####[1416]####
        // ensure Method variable is set//####[1416]####
        if (__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method == null) {//####[1416]####
            __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_ensureMethodVarSet();//####[1416]####
        }//####[1416]####
        taskinfo.setParameters(__omp_vars);//####[1416]####
        taskinfo.setMethod(__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method);//####[1416]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1416]####
    }//####[1416]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing15> __omp_vars) {//####[1416]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1416]####
        return _ompParallelRegion_15(__omp_vars, new TaskInfo());//####[1416]####
    }//####[1416]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing15> __omp_vars, TaskInfo taskinfo) {//####[1416]####
        // ensure Method variable is set//####[1416]####
        if (__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method == null) {//####[1416]####
            __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_ensureMethodVarSet();//####[1416]####
        }//####[1416]####
        taskinfo.setTaskIdArgIndexes(0);//####[1416]####
        taskinfo.addDependsOn(__omp_vars);//####[1416]####
        taskinfo.setParameters(__omp_vars);//####[1416]####
        taskinfo.setMethod(__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method);//####[1416]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1416]####
    }//####[1416]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing15> __omp_vars) {//####[1416]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1416]####
        return _ompParallelRegion_15(__omp_vars, new TaskInfo());//####[1416]####
    }//####[1416]####
    private static TaskIDGroup<Void> _ompParallelRegion_15(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing15> __omp_vars, TaskInfo taskinfo) {//####[1416]####
        // ensure Method variable is set//####[1416]####
        if (__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method == null) {//####[1416]####
            __pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_ensureMethodVarSet();//####[1416]####
        }//####[1416]####
        taskinfo.setQueueArgIndexes(0);//####[1416]####
        taskinfo.setIsPipeline(true);//####[1416]####
        taskinfo.setParameters(__omp_vars);//####[1416]####
        taskinfo.setMethod(__pt___ompParallelRegion_15__omp__parallelRegionVarHolderClass_BitmapProcessing15_method);//####[1416]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1416]####
    }//####[1416]####
    public static void __pt___ompParallelRegion_15(_omp__parallelRegionVarHolderClass_BitmapProcessing15 __omp_vars) {//####[1416]####
        int MAX_VALUE_INT = __omp_vars.MAX_VALUE_INT;//####[1418]####
        int G = __omp_vars.G;//####[1419]####
        int pixel = __omp_vars.pixel;//####[1420]####
        int A = __omp_vars.A;//####[1421]####
        double green = __omp_vars.green;//####[1422]####
        int MAX_SIZE = __omp_vars.MAX_SIZE;//####[1423]####
        int B = __omp_vars.B;//####[1424]####
        int width = __omp_vars.width;//####[1425]####
        int[] gammaB = __omp_vars.gammaB;//####[1426]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1427]####
        double red = __omp_vars.red;//####[1428]####
        int height = __omp_vars.height;//####[1429]####
        double blue = __omp_vars.blue;//####[1430]####
        int[] gammaR = __omp_vars.gammaR;//####[1431]####
        int R = __omp_vars.R;//####[1432]####
        double MAX_VALUE_DBL = __omp_vars.MAX_VALUE_DBL;//####[1433]####
        double REVERSE = __omp_vars.REVERSE;//####[1434]####
        Bitmap src = __omp_vars.src;//####[1435]####
        int[] gammaG = __omp_vars.gammaG;//####[1436]####
        {//####[1437]####
            if (Pyjama.insideParallelRegion()) //####[1438]####
            {//####[1438]####
                boolean _omp_imFirst = _imFirst_17.getAndSet(false);//####[1440]####
                _holderForPIFirst = _imFirst_17;//####[1441]####
                if (_omp_imFirst) //####[1442]####
                {//####[1442]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing17_variables();//####[1443]####
                    int __omp_size_ = 0;//####[1444]####
                    for (int i = 0; i < MAX_SIZE; i = i + 1) //####[1446]####
                    {//####[1446]####
                        _lastElement_17 = i;//####[1447]####
                        __omp_size_++;//####[1448]####
                    }//####[1449]####
                    _pi_17 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[1450]####
                    _omp_piVarContainer.add(_pi_17);//####[1451]####
                    _pi_17.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[1452]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.MAX_SIZE = MAX_SIZE;//####[1454]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.gammaR = gammaR;//####[1455]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.gammaG = gammaG;//####[1456]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.gammaB = gammaB;//####[1457]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.MAX_VALUE_INT = MAX_VALUE_INT;//####[1458]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1459]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.REVERSE = REVERSE;//####[1460]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.red = red;//####[1461]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.green = green;//####[1462]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.blue = blue;//####[1463]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.G = G;//####[1464]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.A = A;//####[1465]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.pixel = pixel;//####[1466]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.B = B;//####[1467]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.width = width;//####[1468]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.bmOut = bmOut;//####[1469]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.height = height;//####[1470]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.R = R;//####[1471]####
                    _ompWorkSharedUserCode_BitmapProcessing17_variables_instance.src = src;//####[1472]####
                    _waitBarrier_17.countDown();//####[1473]####
                } else {//####[1474]####
                    try {//####[1475]####
                        _waitBarrier_17.await();//####[1475]####
                    } catch (InterruptedException __omp__ie) {//####[1475]####
                        __omp__ie.printStackTrace();//####[1475]####
                    }//####[1475]####
                }//####[1476]####
                _ompWorkSharedUserCode_BitmapProcessing17(_ompWorkSharedUserCode_BitmapProcessing17_variables_instance);//####[1477]####
                if (_imFinishedCounter_17.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[1478]####
                {//####[1478]####
                    _waitBarrierAfter_17.countDown();//####[1479]####
                } else {//####[1480]####
                    try {//####[1481]####
                        _waitBarrierAfter_17.await();//####[1482]####
                    } catch (InterruptedException __omp__ie) {//####[1483]####
                        __omp__ie.printStackTrace();//####[1484]####
                    }//####[1485]####
                }//####[1486]####
            } else {//####[1489]####
                for (int i = 0; i < MAX_SIZE; i = i + 1) //####[1491]####
                {//####[1491]####
                    __omp_vars.gammaR[i] = (int) Math.min(__omp_vars.MAX_VALUE_INT, (int) ((__omp_vars.MAX_VALUE_DBL * Math.pow(i / __omp_vars.MAX_VALUE_DBL, __omp_vars.REVERSE / __omp_vars.red)) + 0.5));//####[1492]####
                    __omp_vars.gammaG[i] = (int) Math.min(__omp_vars.MAX_VALUE_INT, (int) ((__omp_vars.MAX_VALUE_DBL * Math.pow(i / __omp_vars.MAX_VALUE_DBL, __omp_vars.REVERSE / __omp_vars.green)) + 0.5));//####[1493]####
                    __omp_vars.gammaB[i] = (int) Math.min(__omp_vars.MAX_VALUE_INT, (int) ((__omp_vars.MAX_VALUE_DBL * Math.pow(i / __omp_vars.MAX_VALUE_DBL, __omp_vars.REVERSE / __omp_vars.blue)) + 0.5));//####[1494]####
                }//####[1495]####
            }//####[1496]####
        }//####[1498]####
        __omp_vars.MAX_VALUE_INT = MAX_VALUE_INT;//####[1500]####
        __omp_vars.G = G;//####[1501]####
        __omp_vars.pixel = pixel;//####[1502]####
        __omp_vars.A = A;//####[1503]####
        __omp_vars.green = green;//####[1504]####
        __omp_vars.MAX_SIZE = MAX_SIZE;//####[1505]####
        __omp_vars.B = B;//####[1506]####
        __omp_vars.width = width;//####[1507]####
        __omp_vars.gammaB = gammaB;//####[1508]####
        __omp_vars.bmOut = bmOut;//####[1509]####
        __omp_vars.red = red;//####[1510]####
        __omp_vars.height = height;//####[1511]####
        __omp_vars.blue = blue;//####[1512]####
        __omp_vars.gammaR = gammaR;//####[1513]####
        __omp_vars.R = R;//####[1514]####
        __omp_vars.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1515]####
        __omp_vars.REVERSE = REVERSE;//####[1516]####
        __omp_vars.src = src;//####[1517]####
        __omp_vars.gammaG = gammaG;//####[1518]####
    }//####[1519]####
//####[1519]####
//####[1519]####
    private static AtomicBoolean _imFirst_20 = new AtomicBoolean(true);//####[1519]####
//####[1520]####
    private static AtomicInteger _imFinishedCounter_20 = new AtomicInteger(0);//####[1520]####
//####[1521]####
    private static CountDownLatch _waitBarrier_20 = new CountDownLatch(1);//####[1521]####
//####[1522]####
    private static CountDownLatch _waitBarrierAfter_20 = new CountDownLatch(1);//####[1522]####
//####[1523]####
    private static ParIterator<Integer> _pi_20 = null;//####[1523]####
//####[1524]####
    private static Integer _lastElement_20 = null;//####[1524]####
//####[1525]####
    private static _ompWorkSharedUserCode_BitmapProcessing20_variables _ompWorkSharedUserCode_BitmapProcessing20_variables_instance = null;//####[1525]####
//####[1526]####
    private static void _ompWorkSharedUserCode_BitmapProcessing20(_ompWorkSharedUserCode_BitmapProcessing20_variables __omp_vars) {//####[1526]####
        int width = __omp_vars.width;//####[1528]####
        int height = __omp_vars.height;//####[1529]####
        int A = __omp_vars.A;//####[1530]####
        int R = __omp_vars.R;//####[1531]####
        int G = __omp_vars.G;//####[1532]####
        int B = __omp_vars.B;//####[1533]####
        int pixel = __omp_vars.pixel;//####[1534]####
        int[] gammaR = __omp_vars.gammaR;//####[1535]####
        int[] gammaG = __omp_vars.gammaG;//####[1536]####
        int[] gammaB = __omp_vars.gammaB;//####[1537]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1538]####
        int MAX_VALUE_INT = __omp_vars.MAX_VALUE_INT;//####[1539]####
        int MAX_SIZE = __omp_vars.MAX_SIZE;//####[1540]####
        double green = __omp_vars.green;//####[1541]####
        double red = __omp_vars.red;//####[1542]####
        double blue = __omp_vars.blue;//####[1543]####
        double MAX_VALUE_DBL = __omp_vars.MAX_VALUE_DBL;//####[1544]####
        double REVERSE = __omp_vars.REVERSE;//####[1545]####
        Bitmap src = __omp_vars.src;//####[1546]####
        Integer x;//####[1547]####
        while (_pi_20.hasNext()) //####[1548]####
        {//####[1548]####
            x = _pi_20.next();//####[1549]####
            {//####[1551]####
                for (int y = 0; y < height; y++) //####[1552]####
                {//####[1552]####
                    pixel = src.getPixel(x, y);//####[1553]####
                    A = Color.alpha(pixel);//####[1554]####
                    R = gammaR[Color.red(pixel)];//####[1555]####
                    G = gammaG[Color.green(pixel)];//####[1556]####
                    B = gammaB[Color.blue(pixel)];//####[1557]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1558]####
                }//####[1559]####
            }//####[1560]####
        }//####[1561]####
        __omp_vars.MAX_VALUE_INT = MAX_VALUE_INT;//####[1563]####
        __omp_vars.MAX_SIZE = MAX_SIZE;//####[1564]####
        __omp_vars.green = green;//####[1565]####
        __omp_vars.red = red;//####[1566]####
        __omp_vars.blue = blue;//####[1567]####
        __omp_vars.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1568]####
        __omp_vars.REVERSE = REVERSE;//####[1569]####
        __omp_vars.src = src;//####[1570]####
    }//####[1571]####
//####[1575]####
    private static volatile Method __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method = null;//####[1575]####
    private synchronized static void __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_ensureMethodVarSet() {//####[1575]####
        if (__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method == null) {//####[1575]####
            try {//####[1575]####
                __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_18", new Class[] {//####[1575]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing18.class//####[1575]####
                });//####[1575]####
            } catch (Exception e) {//####[1575]####
                e.printStackTrace();//####[1575]####
            }//####[1575]####
        }//####[1575]####
    }//####[1575]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(_omp__parallelRegionVarHolderClass_BitmapProcessing18 __omp_vars) {//####[1575]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1575]####
        return _ompParallelRegion_18(__omp_vars, new TaskInfo());//####[1575]####
    }//####[1575]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(_omp__parallelRegionVarHolderClass_BitmapProcessing18 __omp_vars, TaskInfo taskinfo) {//####[1575]####
        // ensure Method variable is set//####[1575]####
        if (__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method == null) {//####[1575]####
            __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_ensureMethodVarSet();//####[1575]####
        }//####[1575]####
        taskinfo.setParameters(__omp_vars);//####[1575]####
        taskinfo.setMethod(__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method);//####[1575]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1575]####
    }//####[1575]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing18> __omp_vars) {//####[1575]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1575]####
        return _ompParallelRegion_18(__omp_vars, new TaskInfo());//####[1575]####
    }//####[1575]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing18> __omp_vars, TaskInfo taskinfo) {//####[1575]####
        // ensure Method variable is set//####[1575]####
        if (__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method == null) {//####[1575]####
            __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_ensureMethodVarSet();//####[1575]####
        }//####[1575]####
        taskinfo.setTaskIdArgIndexes(0);//####[1575]####
        taskinfo.addDependsOn(__omp_vars);//####[1575]####
        taskinfo.setParameters(__omp_vars);//####[1575]####
        taskinfo.setMethod(__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method);//####[1575]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1575]####
    }//####[1575]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing18> __omp_vars) {//####[1575]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1575]####
        return _ompParallelRegion_18(__omp_vars, new TaskInfo());//####[1575]####
    }//####[1575]####
    private static TaskIDGroup<Void> _ompParallelRegion_18(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing18> __omp_vars, TaskInfo taskinfo) {//####[1575]####
        // ensure Method variable is set//####[1575]####
        if (__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method == null) {//####[1575]####
            __pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_ensureMethodVarSet();//####[1575]####
        }//####[1575]####
        taskinfo.setQueueArgIndexes(0);//####[1575]####
        taskinfo.setIsPipeline(true);//####[1575]####
        taskinfo.setParameters(__omp_vars);//####[1575]####
        taskinfo.setMethod(__pt___ompParallelRegion_18__omp__parallelRegionVarHolderClass_BitmapProcessing18_method);//####[1575]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1575]####
    }//####[1575]####
    public static void __pt___ompParallelRegion_18(_omp__parallelRegionVarHolderClass_BitmapProcessing18 __omp_vars) {//####[1575]####
        int G = __omp_vars.G;//####[1577]####
        int MAX_VALUE_INT = __omp_vars.MAX_VALUE_INT;//####[1578]####
        int A = __omp_vars.A;//####[1579]####
        int pixel = __omp_vars.pixel;//####[1580]####
        int B = __omp_vars.B;//####[1581]####
        int MAX_SIZE = __omp_vars.MAX_SIZE;//####[1582]####
        double green = __omp_vars.green;//####[1583]####
        int width = __omp_vars.width;//####[1584]####
        int[] gammaB = __omp_vars.gammaB;//####[1585]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1586]####
        int height = __omp_vars.height;//####[1587]####
        double red = __omp_vars.red;//####[1588]####
        double blue = __omp_vars.blue;//####[1589]####
        int[] gammaR = __omp_vars.gammaR;//####[1590]####
        int R = __omp_vars.R;//####[1591]####
        double MAX_VALUE_DBL = __omp_vars.MAX_VALUE_DBL;//####[1592]####
        double REVERSE = __omp_vars.REVERSE;//####[1593]####
        Bitmap src = __omp_vars.src;//####[1594]####
        int[] gammaG = __omp_vars.gammaG;//####[1595]####
        {//####[1596]####
            if (Pyjama.insideParallelRegion()) //####[1597]####
            {//####[1597]####
                boolean _omp_imFirst = _imFirst_20.getAndSet(false);//####[1599]####
                _holderForPIFirst = _imFirst_20;//####[1600]####
                if (_omp_imFirst) //####[1601]####
                {//####[1601]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing20_variables();//####[1602]####
                    int __omp_size_ = 0;//####[1603]####
                    for (int x = 0; x < width; x = x + 1) //####[1605]####
                    {//####[1605]####
                        _lastElement_20 = x;//####[1606]####
                        __omp_size_++;//####[1607]####
                    }//####[1608]####
                    _pi_20 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[1609]####
                    _omp_piVarContainer.add(_pi_20);//####[1610]####
                    _pi_20.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[1611]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.width = width;//####[1613]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.height = height;//####[1614]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.A = A;//####[1615]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.R = R;//####[1616]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.G = G;//####[1617]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.B = B;//####[1618]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.pixel = pixel;//####[1619]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.gammaR = gammaR;//####[1620]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.gammaG = gammaG;//####[1621]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.gammaB = gammaB;//####[1622]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.bmOut = bmOut;//####[1623]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.MAX_VALUE_INT = MAX_VALUE_INT;//####[1624]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.MAX_SIZE = MAX_SIZE;//####[1625]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.green = green;//####[1626]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.red = red;//####[1627]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.blue = blue;//####[1628]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1629]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.REVERSE = REVERSE;//####[1630]####
                    _ompWorkSharedUserCode_BitmapProcessing20_variables_instance.src = src;//####[1631]####
                    _waitBarrier_20.countDown();//####[1632]####
                } else {//####[1633]####
                    try {//####[1634]####
                        _waitBarrier_20.await();//####[1634]####
                    } catch (InterruptedException __omp__ie) {//####[1634]####
                        __omp__ie.printStackTrace();//####[1634]####
                    }//####[1634]####
                }//####[1635]####
                _ompWorkSharedUserCode_BitmapProcessing20(_ompWorkSharedUserCode_BitmapProcessing20_variables_instance);//####[1636]####
                if (_imFinishedCounter_20.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[1637]####
                {//####[1637]####
                    _waitBarrierAfter_20.countDown();//####[1638]####
                } else {//####[1639]####
                    try {//####[1640]####
                        _waitBarrierAfter_20.await();//####[1641]####
                    } catch (InterruptedException __omp__ie) {//####[1642]####
                        __omp__ie.printStackTrace();//####[1643]####
                    }//####[1644]####
                }//####[1645]####
            } else {//####[1648]####
                for (int x = 0; x < width; x = x + 1) //####[1650]####
                {//####[1650]####
                    for (int y = 0; y < __omp_vars.height; y++) //####[1651]####
                    {//####[1651]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[1652]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[1653]####
                        __omp_vars.R = __omp_vars.gammaR[Color.red(__omp_vars.pixel)];//####[1654]####
                        __omp_vars.G = __omp_vars.gammaG[Color.green(__omp_vars.pixel)];//####[1655]####
                        __omp_vars.B = __omp_vars.gammaB[Color.blue(__omp_vars.pixel)];//####[1656]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[1657]####
                    }//####[1658]####
                }//####[1659]####
            }//####[1660]####
        }//####[1662]####
        __omp_vars.G = G;//####[1664]####
        __omp_vars.MAX_VALUE_INT = MAX_VALUE_INT;//####[1665]####
        __omp_vars.A = A;//####[1666]####
        __omp_vars.pixel = pixel;//####[1667]####
        __omp_vars.B = B;//####[1668]####
        __omp_vars.MAX_SIZE = MAX_SIZE;//####[1669]####
        __omp_vars.green = green;//####[1670]####
        __omp_vars.width = width;//####[1671]####
        __omp_vars.gammaB = gammaB;//####[1672]####
        __omp_vars.bmOut = bmOut;//####[1673]####
        __omp_vars.height = height;//####[1674]####
        __omp_vars.red = red;//####[1675]####
        __omp_vars.blue = blue;//####[1676]####
        __omp_vars.gammaR = gammaR;//####[1677]####
        __omp_vars.R = R;//####[1678]####
        __omp_vars.MAX_VALUE_DBL = MAX_VALUE_DBL;//####[1679]####
        __omp_vars.REVERSE = REVERSE;//####[1680]####
        __omp_vars.src = src;//####[1681]####
        __omp_vars.gammaG = gammaG;//####[1682]####
    }//####[1683]####
//####[1683]####
//####[1684]####
    public static Bitmap contrast(Bitmap src, double value) {//####[1684]####
        {//####[1684]####
            int width = src.getWidth();//####[1685]####
            int height = src.getHeight();//####[1686]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[1687]####
            Canvas c = new Canvas();//####[1688]####
            c.setBitmap(bmOut);//####[1689]####
            c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));//####[1690]####
            int A = 0, R = 0, G = 0, B = 0;//####[1691]####
            int pixel = 0;//####[1692]####
            double contrast = Math.pow((100 + value) / 100, 2);//####[1693]####
            if (Pyjama.insideParallelRegion()) //####[1695]####
            {//####[1695]####
                {//####[1697]####
                    for (int x = 0; x < width; x = x + 1) //####[1698]####
                    {//####[1698]####
                        for (int y = 0; y < height; ++y) //####[1699]####
                        {//####[1699]####
                            pixel = src.getPixel(x, y);//####[1700]####
                            A = Color.alpha(pixel);//####[1701]####
                            R = Color.red(pixel);//####[1702]####
                            R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1703]####
                            if (R < 0) //####[1704]####
                            {//####[1704]####
                                R = 0;//####[1705]####
                            } else if (R > 255) //####[1706]####
                            {//####[1706]####
                                R = 255;//####[1707]####
                            }//####[1708]####
                            G = Color.green(pixel);//####[1709]####
                            G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1710]####
                            if (G < 0) //####[1711]####
                            {//####[1711]####
                                G = 0;//####[1712]####
                            } else if (G > 255) //####[1713]####
                            {//####[1713]####
                                G = 255;//####[1714]####
                            }//####[1715]####
                            B = Color.blue(pixel);//####[1716]####
                            B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1717]####
                            if (B < 0) //####[1718]####
                            {//####[1718]####
                                B = 0;//####[1719]####
                            } else if (B > 255) //####[1720]####
                            {//####[1720]####
                                B = 255;//####[1721]####
                            }//####[1722]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1723]####
                        }//####[1724]####
                    }//####[1725]####
                }//####[1726]####
            } else {//####[1727]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[1729]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing21 _omp__parallelRegionVarHolderInstance_21 = new _omp__parallelRegionVarHolderClass_BitmapProcessing21();//####[1732]####
                _omp__parallelRegionVarHolderInstance_21.width = width;//####[1735]####
                _omp__parallelRegionVarHolderInstance_21.height = height;//####[1736]####
                _omp__parallelRegionVarHolderInstance_21.A = A;//####[1737]####
                _omp__parallelRegionVarHolderInstance_21.R = R;//####[1738]####
                _omp__parallelRegionVarHolderInstance_21.G = G;//####[1739]####
                _omp__parallelRegionVarHolderInstance_21.B = B;//####[1740]####
                _omp__parallelRegionVarHolderInstance_21.pixel = pixel;//####[1741]####
                _omp__parallelRegionVarHolderInstance_21.bmOut = bmOut;//####[1742]####
                _omp__parallelRegionVarHolderInstance_21.c = c;//####[1743]####
                _omp__parallelRegionVarHolderInstance_21.contrast = contrast;//####[1744]####
                _omp__parallelRegionVarHolderInstance_21.value = value;//####[1745]####
                _omp__parallelRegionVarHolderInstance_21.src = src;//####[1746]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[1749]####
                TaskID _omp__parallelRegionTaskID_21 = _ompParallelRegion_21(_omp__parallelRegionVarHolderInstance_21);//####[1750]####
                __pt___ompParallelRegion_21(_omp__parallelRegionVarHolderInstance_21);//####[1751]####
                try {//####[1752]####
                    _omp__parallelRegionTaskID_21.waitTillFinished();//####[1752]####
                } catch (Exception __pt__ex) {//####[1752]####
                    __pt__ex.printStackTrace();//####[1752]####
                }//####[1752]####
                PJPackageOnly.setMasterThread(null);//####[1754]####
                _holderForPIFirst.set(true);//####[1755]####
                width = _omp__parallelRegionVarHolderInstance_21.width;//####[1757]####
                height = _omp__parallelRegionVarHolderInstance_21.height;//####[1758]####
                A = _omp__parallelRegionVarHolderInstance_21.A;//####[1759]####
                R = _omp__parallelRegionVarHolderInstance_21.R;//####[1760]####
                G = _omp__parallelRegionVarHolderInstance_21.G;//####[1761]####
                B = _omp__parallelRegionVarHolderInstance_21.B;//####[1762]####
                pixel = _omp__parallelRegionVarHolderInstance_21.pixel;//####[1763]####
                bmOut = _omp__parallelRegionVarHolderInstance_21.bmOut;//####[1764]####
                c = _omp__parallelRegionVarHolderInstance_21.c;//####[1765]####
                contrast = _omp__parallelRegionVarHolderInstance_21.contrast;//####[1766]####
                value = _omp__parallelRegionVarHolderInstance_21.value;//####[1767]####
                src = _omp__parallelRegionVarHolderInstance_21.src;//####[1768]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[1769]####
            }//####[1770]####
            src.recycle();//####[1773]####
            src = null;//####[1774]####
            return bmOut;//####[1775]####
        }//####[1776]####
    }//####[1777]####
//####[1778]####
    private static AtomicBoolean _imFirst_23 = new AtomicBoolean(true);//####[1778]####
//####[1779]####
    private static AtomicInteger _imFinishedCounter_23 = new AtomicInteger(0);//####[1779]####
//####[1780]####
    private static CountDownLatch _waitBarrier_23 = new CountDownLatch(1);//####[1780]####
//####[1781]####
    private static CountDownLatch _waitBarrierAfter_23 = new CountDownLatch(1);//####[1781]####
//####[1782]####
    private static ParIterator<Integer> _pi_23 = null;//####[1782]####
//####[1783]####
    private static Integer _lastElement_23 = null;//####[1783]####
//####[1784]####
    private static _ompWorkSharedUserCode_BitmapProcessing23_variables _ompWorkSharedUserCode_BitmapProcessing23_variables_instance = null;//####[1784]####
//####[1785]####
    private static void _ompWorkSharedUserCode_BitmapProcessing23(_ompWorkSharedUserCode_BitmapProcessing23_variables __omp_vars) {//####[1785]####
        int G = __omp_vars.G;//####[1787]####
        int pixel = __omp_vars.pixel;//####[1788]####
        int A = __omp_vars.A;//####[1789]####
        Canvas c = __omp_vars.c;//####[1790]####
        int B = __omp_vars.B;//####[1791]####
        int width = __omp_vars.width;//####[1792]####
        double contrast = __omp_vars.contrast;//####[1793]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1794]####
        int height = __omp_vars.height;//####[1795]####
        int R = __omp_vars.R;//####[1796]####
        double value = __omp_vars.value;//####[1797]####
        Bitmap src = __omp_vars.src;//####[1798]####
        Integer x;//####[1799]####
        while (_pi_23.hasNext()) //####[1800]####
        {//####[1800]####
            x = _pi_23.next();//####[1801]####
            {//####[1803]####
                for (int y = 0; y < height; ++y) //####[1804]####
                {//####[1804]####
                    pixel = src.getPixel(x, y);//####[1805]####
                    A = Color.alpha(pixel);//####[1806]####
                    R = Color.red(pixel);//####[1807]####
                    R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1808]####
                    if (R < 0) //####[1809]####
                    {//####[1809]####
                        R = 0;//####[1810]####
                    } else if (R > 255) //####[1811]####
                    {//####[1811]####
                        R = 255;//####[1812]####
                    }//####[1813]####
                    G = Color.green(pixel);//####[1814]####
                    G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1815]####
                    if (G < 0) //####[1816]####
                    {//####[1816]####
                        G = 0;//####[1817]####
                    } else if (G > 255) //####[1818]####
                    {//####[1818]####
                        G = 255;//####[1819]####
                    }//####[1820]####
                    B = Color.blue(pixel);//####[1821]####
                    B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);//####[1822]####
                    if (B < 0) //####[1823]####
                    {//####[1823]####
                        B = 0;//####[1824]####
                    } else if (B > 255) //####[1825]####
                    {//####[1825]####
                        B = 255;//####[1826]####
                    }//####[1827]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[1828]####
                }//####[1829]####
            }//####[1830]####
        }//####[1831]####
        __omp_vars.G = G;//####[1833]####
        __omp_vars.pixel = pixel;//####[1834]####
        __omp_vars.A = A;//####[1835]####
        __omp_vars.c = c;//####[1836]####
        __omp_vars.B = B;//####[1837]####
        __omp_vars.width = width;//####[1838]####
        __omp_vars.contrast = contrast;//####[1839]####
        __omp_vars.bmOut = bmOut;//####[1840]####
        __omp_vars.height = height;//####[1841]####
        __omp_vars.R = R;//####[1842]####
        __omp_vars.value = value;//####[1843]####
        __omp_vars.src = src;//####[1844]####
    }//####[1845]####
//####[1849]####
    private static volatile Method __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method = null;//####[1849]####
    private synchronized static void __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_ensureMethodVarSet() {//####[1849]####
        if (__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method == null) {//####[1849]####
            try {//####[1849]####
                __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_21", new Class[] {//####[1849]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing21.class//####[1849]####
                });//####[1849]####
            } catch (Exception e) {//####[1849]####
                e.printStackTrace();//####[1849]####
            }//####[1849]####
        }//####[1849]####
    }//####[1849]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(_omp__parallelRegionVarHolderClass_BitmapProcessing21 __omp_vars) {//####[1849]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1849]####
        return _ompParallelRegion_21(__omp_vars, new TaskInfo());//####[1849]####
    }//####[1849]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(_omp__parallelRegionVarHolderClass_BitmapProcessing21 __omp_vars, TaskInfo taskinfo) {//####[1849]####
        // ensure Method variable is set//####[1849]####
        if (__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method == null) {//####[1849]####
            __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_ensureMethodVarSet();//####[1849]####
        }//####[1849]####
        taskinfo.setParameters(__omp_vars);//####[1849]####
        taskinfo.setMethod(__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method);//####[1849]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1849]####
    }//####[1849]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing21> __omp_vars) {//####[1849]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1849]####
        return _ompParallelRegion_21(__omp_vars, new TaskInfo());//####[1849]####
    }//####[1849]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing21> __omp_vars, TaskInfo taskinfo) {//####[1849]####
        // ensure Method variable is set//####[1849]####
        if (__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method == null) {//####[1849]####
            __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_ensureMethodVarSet();//####[1849]####
        }//####[1849]####
        taskinfo.setTaskIdArgIndexes(0);//####[1849]####
        taskinfo.addDependsOn(__omp_vars);//####[1849]####
        taskinfo.setParameters(__omp_vars);//####[1849]####
        taskinfo.setMethod(__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method);//####[1849]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1849]####
    }//####[1849]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing21> __omp_vars) {//####[1849]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[1849]####
        return _ompParallelRegion_21(__omp_vars, new TaskInfo());//####[1849]####
    }//####[1849]####
    private static TaskIDGroup<Void> _ompParallelRegion_21(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing21> __omp_vars, TaskInfo taskinfo) {//####[1849]####
        // ensure Method variable is set//####[1849]####
        if (__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method == null) {//####[1849]####
            __pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_ensureMethodVarSet();//####[1849]####
        }//####[1849]####
        taskinfo.setQueueArgIndexes(0);//####[1849]####
        taskinfo.setIsPipeline(true);//####[1849]####
        taskinfo.setParameters(__omp_vars);//####[1849]####
        taskinfo.setMethod(__pt___ompParallelRegion_21__omp__parallelRegionVarHolderClass_BitmapProcessing21_method);//####[1849]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[1849]####
    }//####[1849]####
    public static void __pt___ompParallelRegion_21(_omp__parallelRegionVarHolderClass_BitmapProcessing21 __omp_vars) {//####[1849]####
        int width = __omp_vars.width;//####[1851]####
        int height = __omp_vars.height;//####[1852]####
        int A = __omp_vars.A;//####[1853]####
        int R = __omp_vars.R;//####[1854]####
        int G = __omp_vars.G;//####[1855]####
        int B = __omp_vars.B;//####[1856]####
        int pixel = __omp_vars.pixel;//####[1857]####
        Bitmap bmOut = __omp_vars.bmOut;//####[1858]####
        Canvas c = __omp_vars.c;//####[1859]####
        double contrast = __omp_vars.contrast;//####[1860]####
        double value = __omp_vars.value;//####[1861]####
        Bitmap src = __omp_vars.src;//####[1862]####
        {//####[1863]####
            if (Pyjama.insideParallelRegion()) //####[1864]####
            {//####[1864]####
                boolean _omp_imFirst = _imFirst_23.getAndSet(false);//####[1866]####
                _holderForPIFirst = _imFirst_23;//####[1867]####
                if (_omp_imFirst) //####[1868]####
                {//####[1868]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing23_variables();//####[1869]####
                    int __omp_size_ = 0;//####[1870]####
                    for (int x = 0; x < width; x = x + 1) //####[1872]####
                    {//####[1872]####
                        _lastElement_23 = x;//####[1873]####
                        __omp_size_++;//####[1874]####
                    }//####[1875]####
                    _pi_23 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[1876]####
                    _omp_piVarContainer.add(_pi_23);//####[1877]####
                    _pi_23.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[1878]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.G = G;//####[1879]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.pixel = pixel;//####[1880]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.A = A;//####[1881]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.c = c;//####[1882]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.B = B;//####[1883]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.width = width;//####[1884]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.contrast = contrast;//####[1885]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.bmOut = bmOut;//####[1886]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.height = height;//####[1887]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.R = R;//####[1888]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.value = value;//####[1889]####
                    _ompWorkSharedUserCode_BitmapProcessing23_variables_instance.src = src;//####[1890]####
                    _waitBarrier_23.countDown();//####[1891]####
                } else {//####[1892]####
                    try {//####[1893]####
                        _waitBarrier_23.await();//####[1893]####
                    } catch (InterruptedException __omp__ie) {//####[1893]####
                        __omp__ie.printStackTrace();//####[1893]####
                    }//####[1893]####
                }//####[1894]####
                _ompWorkSharedUserCode_BitmapProcessing23(_ompWorkSharedUserCode_BitmapProcessing23_variables_instance);//####[1895]####
                if (_imFinishedCounter_23.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[1896]####
                {//####[1896]####
                    _waitBarrierAfter_23.countDown();//####[1897]####
                } else {//####[1898]####
                    try {//####[1899]####
                        _waitBarrierAfter_23.await();//####[1900]####
                    } catch (InterruptedException __omp__ie) {//####[1901]####
                        __omp__ie.printStackTrace();//####[1902]####
                    }//####[1903]####
                }//####[1904]####
            } else {//####[1907]####
                for (int x = 0; x < width; x = x + 1) //####[1909]####
                {//####[1909]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[1910]####
                    {//####[1910]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[1911]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[1912]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[1913]####
                        __omp_vars.R = (int) (((((__omp_vars.R / 255.0) - 0.5) * __omp_vars.contrast) + 0.5) * 255.0);//####[1914]####
                        if (__omp_vars.R < 0) //####[1915]####
                        {//####[1915]####
                            __omp_vars.R = 0;//####[1916]####
                        } else if (__omp_vars.R > 255) //####[1917]####
                        {//####[1917]####
                            __omp_vars.R = 255;//####[1918]####
                        }//####[1919]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[1920]####
                        __omp_vars.G = (int) (((((__omp_vars.G / 255.0) - 0.5) * __omp_vars.contrast) + 0.5) * 255.0);//####[1921]####
                        if (__omp_vars.G < 0) //####[1922]####
                        {//####[1922]####
                            __omp_vars.G = 0;//####[1923]####
                        } else if (__omp_vars.G > 255) //####[1924]####
                        {//####[1924]####
                            __omp_vars.G = 255;//####[1925]####
                        }//####[1926]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[1927]####
                        __omp_vars.B = (int) (((((__omp_vars.B / 255.0) - 0.5) * __omp_vars.contrast) + 0.5) * 255.0);//####[1928]####
                        if (__omp_vars.B < 0) //####[1929]####
                        {//####[1929]####
                            __omp_vars.B = 0;//####[1930]####
                        } else if (__omp_vars.B > 255) //####[1931]####
                        {//####[1931]####
                            __omp_vars.B = 255;//####[1932]####
                        }//####[1933]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[1934]####
                    }//####[1935]####
                }//####[1936]####
            }//####[1937]####
        }//####[1939]####
        __omp_vars.c = c;//####[1941]####
        __omp_vars.contrast = contrast;//####[1942]####
        __omp_vars.value = value;//####[1943]####
        __omp_vars.src = src;//####[1944]####
    }//####[1945]####
//####[1945]####
//####[1946]####
    public static Bitmap saturation(Bitmap src, int value) {//####[1946]####
        {//####[1946]####
            float f_value = (float) (value / 100.0);//####[1947]####
            int w = src.getWidth();//####[1948]####
            int h = src.getHeight();//####[1949]####
            Bitmap bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//####[1950]####
            Canvas canvasResult = new Canvas(bitmapResult);//####[1951]####
            Paint paint = new Paint();//####[1952]####
            ColorMatrix colorMatrix = new ColorMatrix();//####[1953]####
            colorMatrix.setSaturation(f_value);//####[1954]####
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);//####[1955]####
            paint.setColorFilter(filter);//####[1956]####
            canvasResult.drawBitmap(src, 0, 0, paint);//####[1957]####
            src.recycle();//####[1958]####
            src = null;//####[1959]####
            return bitmapResult;//####[1960]####
        }//####[1961]####
    }//####[1962]####
//####[1964]####
    public static Bitmap grayscale(Bitmap src) {//####[1964]####
        {//####[1964]####
            float[] GrayArray = { 0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f };//####[1965]####
            ColorMatrix colorMatrixGray = new ColorMatrix(GrayArray);//####[1966]####
            int w = src.getWidth();//####[1967]####
            int h = src.getHeight();//####[1968]####
            Bitmap bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//####[1969]####
            Canvas canvasResult = new Canvas(bitmapResult);//####[1970]####
            Paint paint = new Paint();//####[1971]####
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrixGray);//####[1972]####
            paint.setColorFilter(filter);//####[1973]####
            canvasResult.drawBitmap(src, 0, 0, paint);//####[1974]####
            src.recycle();//####[1975]####
            src = null;//####[1976]####
            return bitmapResult;//####[1977]####
        }//####[1978]####
    }//####[1979]####
//####[1981]####
    public static Bitmap vignette(Bitmap image) {//####[1981]####
        {//####[1981]####
            final int width = image.getWidth();//####[1982]####
            final int height = image.getHeight();//####[1983]####
            float radius = (float) (width / 1.2);//####[1984]####
            int[] colors = new int[] { 0, 0x55000000, 0xff000000 };//####[1985]####
            float[] positions = new float[] { 0.0f, 0.5f, 1.0f };//####[1986]####
            RadialGradient gradient = new RadialGradient(width / 2, height / 2, radius, colors, positions, Shader.TileMode.CLAMP);//####[1987]####
            Canvas canvas = new Canvas(image);//####[1988]####
            canvas.drawARGB(1, 0, 0, 0);//####[1989]####
            final Paint paint = new Paint();//####[1990]####
            paint.setAntiAlias(true);//####[1991]####
            paint.setColor(Color.BLACK);//####[1992]####
            paint.setShader(gradient);//####[1993]####
            final Rect rect = new Rect(0, 0, image.getWidth(), image.getHeight());//####[1994]####
            final RectF rectf = new RectF(rect);//####[1995]####
            canvas.drawRect(rectf, paint);//####[1996]####
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//####[1997]####
            canvas.drawBitmap(image, rect, rect, paint);//####[1998]####
            return image;//####[1999]####
        }//####[2000]####
    }//####[2001]####
//####[2003]####
    public static Bitmap hue(Bitmap bitmap, float hue) {//####[2003]####
        {//####[2003]####
            Bitmap newBitmap = bitmap.copy(bitmap.getConfig(), true);//####[2004]####
            int width = newBitmap.getWidth();//####[2005]####
            int height = newBitmap.getHeight();//####[2006]####
            float[] hsv = new float[3];//####[2007]####
            float _hue = hue;//####[2008]####
            if (Pyjama.insideParallelRegion()) //####[2010]####
            {//####[2010]####
                {//####[2012]####
                    for (int y = 0; y < height; y = y + 1) //####[2013]####
                    {//####[2013]####
                        for (int x = 0; x < width; x++) //####[2014]####
                        {//####[2014]####
                            int pixel = newBitmap.getPixel(x, y);//####[2015]####
                            Color.colorToHSV(pixel, hsv);//####[2016]####
                            hsv[0] = _hue;//####[2017]####
                            newBitmap.setPixel(x, y, Color.HSVToColor(Color.alpha(pixel), hsv));//####[2018]####
                        }//####[2019]####
                    }//####[2020]####
                }//####[2021]####
            } else {//####[2022]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[2024]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing24 _omp__parallelRegionVarHolderInstance_24 = new _omp__parallelRegionVarHolderClass_BitmapProcessing24();//####[2027]####
                _omp__parallelRegionVarHolderInstance_24.width = width;//####[2030]####
                _omp__parallelRegionVarHolderInstance_24.height = height;//####[2031]####
                _omp__parallelRegionVarHolderInstance_24.hsv = hsv;//####[2032]####
                _omp__parallelRegionVarHolderInstance_24._hue = _hue;//####[2033]####
                _omp__parallelRegionVarHolderInstance_24.newBitmap = newBitmap;//####[2034]####
                _omp__parallelRegionVarHolderInstance_24.bitmap = bitmap;//####[2035]####
                _omp__parallelRegionVarHolderInstance_24.hue = hue;//####[2036]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[2039]####
                TaskID _omp__parallelRegionTaskID_24 = _ompParallelRegion_24(_omp__parallelRegionVarHolderInstance_24);//####[2040]####
                __pt___ompParallelRegion_24(_omp__parallelRegionVarHolderInstance_24);//####[2041]####
                try {//####[2042]####
                    _omp__parallelRegionTaskID_24.waitTillFinished();//####[2042]####
                } catch (Exception __pt__ex) {//####[2042]####
                    __pt__ex.printStackTrace();//####[2042]####
                }//####[2042]####
                PJPackageOnly.setMasterThread(null);//####[2044]####
                _holderForPIFirst.set(true);//####[2045]####
                width = _omp__parallelRegionVarHolderInstance_24.width;//####[2047]####
                height = _omp__parallelRegionVarHolderInstance_24.height;//####[2048]####
                hsv = _omp__parallelRegionVarHolderInstance_24.hsv;//####[2049]####
                _hue = _omp__parallelRegionVarHolderInstance_24._hue;//####[2050]####
                newBitmap = _omp__parallelRegionVarHolderInstance_24.newBitmap;//####[2051]####
                bitmap = _omp__parallelRegionVarHolderInstance_24.bitmap;//####[2052]####
                hue = _omp__parallelRegionVarHolderInstance_24.hue;//####[2053]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[2054]####
            }//####[2055]####
            bitmap.recycle();//####[2058]####
            bitmap = null;//####[2059]####
            return newBitmap;//####[2060]####
        }//####[2061]####
    }//####[2062]####
//####[2063]####
    private static AtomicBoolean _imFirst_26 = new AtomicBoolean(true);//####[2063]####
//####[2064]####
    private static AtomicInteger _imFinishedCounter_26 = new AtomicInteger(0);//####[2064]####
//####[2065]####
    private static CountDownLatch _waitBarrier_26 = new CountDownLatch(1);//####[2065]####
//####[2066]####
    private static CountDownLatch _waitBarrierAfter_26 = new CountDownLatch(1);//####[2066]####
//####[2067]####
    private static ParIterator<Integer> _pi_26 = null;//####[2067]####
//####[2068]####
    private static Integer _lastElement_26 = null;//####[2068]####
//####[2069]####
    private static _ompWorkSharedUserCode_BitmapProcessing26_variables _ompWorkSharedUserCode_BitmapProcessing26_variables_instance = null;//####[2069]####
//####[2070]####
    private static void _ompWorkSharedUserCode_BitmapProcessing26(_ompWorkSharedUserCode_BitmapProcessing26_variables __omp_vars) {//####[2070]####
        Bitmap bitmap = __omp_vars.bitmap;//####[2072]####
        float hue = __omp_vars.hue;//####[2073]####
        int width = __omp_vars.width;//####[2074]####
        int height = __omp_vars.height;//####[2075]####
        float _hue = __omp_vars._hue;//####[2076]####
        float[] hsv = __omp_vars.hsv;//####[2077]####
        Bitmap newBitmap = __omp_vars.newBitmap;//####[2078]####
        Integer y;//####[2079]####
        while (_pi_26.hasNext()) //####[2080]####
        {//####[2080]####
            y = _pi_26.next();//####[2081]####
            {//####[2083]####
                for (int x = 0; x < width; x++) //####[2084]####
                {//####[2084]####
                    int pixel = newBitmap.getPixel(x, y);//####[2085]####
                    Color.colorToHSV(pixel, hsv);//####[2086]####
                    hsv[0] = _hue;//####[2087]####
                    newBitmap.setPixel(x, y, Color.HSVToColor(Color.alpha(pixel), hsv));//####[2088]####
                }//####[2089]####
            }//####[2090]####
        }//####[2091]####
        __omp_vars.bitmap = bitmap;//####[2093]####
        __omp_vars.hue = hue;//####[2094]####
        __omp_vars.width = width;//####[2095]####
        __omp_vars.height = height;//####[2096]####
        __omp_vars._hue = _hue;//####[2097]####
        __omp_vars.hsv = hsv;//####[2098]####
        __omp_vars.newBitmap = newBitmap;//####[2099]####
    }//####[2100]####
//####[2104]####
    private static volatile Method __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method = null;//####[2104]####
    private synchronized static void __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_ensureMethodVarSet() {//####[2104]####
        if (__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method == null) {//####[2104]####
            try {//####[2104]####
                __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_24", new Class[] {//####[2104]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing24.class//####[2104]####
                });//####[2104]####
            } catch (Exception e) {//####[2104]####
                e.printStackTrace();//####[2104]####
            }//####[2104]####
        }//####[2104]####
    }//####[2104]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(_omp__parallelRegionVarHolderClass_BitmapProcessing24 __omp_vars) {//####[2104]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2104]####
        return _ompParallelRegion_24(__omp_vars, new TaskInfo());//####[2104]####
    }//####[2104]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(_omp__parallelRegionVarHolderClass_BitmapProcessing24 __omp_vars, TaskInfo taskinfo) {//####[2104]####
        // ensure Method variable is set//####[2104]####
        if (__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method == null) {//####[2104]####
            __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_ensureMethodVarSet();//####[2104]####
        }//####[2104]####
        taskinfo.setParameters(__omp_vars);//####[2104]####
        taskinfo.setMethod(__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method);//####[2104]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2104]####
    }//####[2104]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing24> __omp_vars) {//####[2104]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2104]####
        return _ompParallelRegion_24(__omp_vars, new TaskInfo());//####[2104]####
    }//####[2104]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing24> __omp_vars, TaskInfo taskinfo) {//####[2104]####
        // ensure Method variable is set//####[2104]####
        if (__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method == null) {//####[2104]####
            __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_ensureMethodVarSet();//####[2104]####
        }//####[2104]####
        taskinfo.setTaskIdArgIndexes(0);//####[2104]####
        taskinfo.addDependsOn(__omp_vars);//####[2104]####
        taskinfo.setParameters(__omp_vars);//####[2104]####
        taskinfo.setMethod(__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method);//####[2104]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2104]####
    }//####[2104]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing24> __omp_vars) {//####[2104]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2104]####
        return _ompParallelRegion_24(__omp_vars, new TaskInfo());//####[2104]####
    }//####[2104]####
    private static TaskIDGroup<Void> _ompParallelRegion_24(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing24> __omp_vars, TaskInfo taskinfo) {//####[2104]####
        // ensure Method variable is set//####[2104]####
        if (__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method == null) {//####[2104]####
            __pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_ensureMethodVarSet();//####[2104]####
        }//####[2104]####
        taskinfo.setQueueArgIndexes(0);//####[2104]####
        taskinfo.setIsPipeline(true);//####[2104]####
        taskinfo.setParameters(__omp_vars);//####[2104]####
        taskinfo.setMethod(__pt___ompParallelRegion_24__omp__parallelRegionVarHolderClass_BitmapProcessing24_method);//####[2104]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2104]####
    }//####[2104]####
    public static void __pt___ompParallelRegion_24(_omp__parallelRegionVarHolderClass_BitmapProcessing24 __omp_vars) {//####[2104]####
        int width = __omp_vars.width;//####[2106]####
        int height = __omp_vars.height;//####[2107]####
        float[] hsv = __omp_vars.hsv;//####[2108]####
        float _hue = __omp_vars._hue;//####[2109]####
        Bitmap newBitmap = __omp_vars.newBitmap;//####[2110]####
        Bitmap bitmap = __omp_vars.bitmap;//####[2111]####
        float hue = __omp_vars.hue;//####[2112]####
        {//####[2113]####
            if (Pyjama.insideParallelRegion()) //####[2114]####
            {//####[2114]####
                boolean _omp_imFirst = _imFirst_26.getAndSet(false);//####[2116]####
                _holderForPIFirst = _imFirst_26;//####[2117]####
                if (_omp_imFirst) //####[2118]####
                {//####[2118]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing26_variables();//####[2119]####
                    int __omp_size_ = 0;//####[2120]####
                    for (int y = 0; y < height; y = y + 1) //####[2122]####
                    {//####[2122]####
                        _lastElement_26 = y;//####[2123]####
                        __omp_size_++;//####[2124]####
                    }//####[2125]####
                    _pi_26 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[2126]####
                    _omp_piVarContainer.add(_pi_26);//####[2127]####
                    _pi_26.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[2128]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.bitmap = bitmap;//####[2129]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.hue = hue;//####[2130]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.width = width;//####[2131]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.height = height;//####[2132]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance._hue = _hue;//####[2133]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.hsv = hsv;//####[2134]####
                    _ompWorkSharedUserCode_BitmapProcessing26_variables_instance.newBitmap = newBitmap;//####[2135]####
                    _waitBarrier_26.countDown();//####[2136]####
                } else {//####[2137]####
                    try {//####[2138]####
                        _waitBarrier_26.await();//####[2138]####
                    } catch (InterruptedException __omp__ie) {//####[2138]####
                        __omp__ie.printStackTrace();//####[2138]####
                    }//####[2138]####
                }//####[2139]####
                _ompWorkSharedUserCode_BitmapProcessing26(_ompWorkSharedUserCode_BitmapProcessing26_variables_instance);//####[2140]####
                if (_imFinishedCounter_26.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[2141]####
                {//####[2141]####
                    _waitBarrierAfter_26.countDown();//####[2142]####
                } else {//####[2143]####
                    try {//####[2144]####
                        _waitBarrierAfter_26.await();//####[2145]####
                    } catch (InterruptedException __omp__ie) {//####[2146]####
                        __omp__ie.printStackTrace();//####[2147]####
                    }//####[2148]####
                }//####[2149]####
            } else {//####[2152]####
                for (int y = 0; y < height; y = y + 1) //####[2154]####
                {//####[2154]####
                    for (int x = 0; x < __omp_vars.width; x++) //####[2155]####
                    {//####[2155]####
                        int pixel = __omp_vars.newBitmap.getPixel(x, y);//####[2156]####
                        Color.colorToHSV(pixel, __omp_vars.hsv);//####[2157]####
                        __omp_vars.hsv[0] = __omp_vars._hue;//####[2158]####
                        __omp_vars.newBitmap.setPixel(x, y, Color.HSVToColor(Color.alpha(pixel), __omp_vars.hsv));//####[2159]####
                    }//####[2160]####
                }//####[2161]####
            }//####[2162]####
        }//####[2164]####
        __omp_vars.bitmap = bitmap;//####[2166]####
        __omp_vars.hue = hue;//####[2167]####
    }//####[2168]####
//####[2168]####
//####[2169]####
    public static Bitmap tint(Bitmap src, int color) {//####[2169]####
        {//####[2169]####
            int width = src.getWidth();//####[2170]####
            int height = src.getHeight();//####[2171]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[2172]####
            Paint p = new Paint(Color.RED);//####[2173]####
            ColorFilter filter = new LightingColorFilter(color, 1);//####[2174]####
            p.setColorFilter(filter);//####[2175]####
            Canvas c = new Canvas();//####[2176]####
            c.setBitmap(bmOut);//####[2177]####
            c.drawBitmap(src, 0, 0, p);//####[2178]####
            src.recycle();//####[2179]####
            src = null;//####[2180]####
            return bmOut;//####[2181]####
        }//####[2182]####
    }//####[2183]####
//####[2185]####
    public static Bitmap invert(Bitmap src) {//####[2185]####
        {//####[2185]####
            Bitmap output = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());//####[2186]####
            int A = 0, R = 0, G = 0, B = 0;//####[2187]####
            int pixelColor = 0;//####[2188]####
            int height = src.getHeight();//####[2189]####
            int width = src.getWidth();//####[2190]####
            if (Pyjama.insideParallelRegion()) //####[2192]####
            {//####[2192]####
                {//####[2194]####
                    for (int y = 0; y < height; y = y + 1) //####[2195]####
                    {//####[2195]####
                        for (int x = 0; x < width; x++) //####[2196]####
                        {//####[2196]####
                            pixelColor = src.getPixel(x, y);//####[2197]####
                            A = Color.alpha(pixelColor);//####[2198]####
                            R = 255 - Color.red(pixelColor);//####[2199]####
                            G = 255 - Color.green(pixelColor);//####[2200]####
                            B = 255 - Color.blue(pixelColor);//####[2201]####
                            output.setPixel(x, y, Color.argb(A, R, G, B));//####[2202]####
                        }//####[2203]####
                    }//####[2204]####
                }//####[2205]####
            } else {//####[2206]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[2208]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing27 _omp__parallelRegionVarHolderInstance_27 = new _omp__parallelRegionVarHolderClass_BitmapProcessing27();//####[2211]####
                _omp__parallelRegionVarHolderInstance_27.width = width;//####[2214]####
                _omp__parallelRegionVarHolderInstance_27.height = height;//####[2215]####
                _omp__parallelRegionVarHolderInstance_27.A = A;//####[2216]####
                _omp__parallelRegionVarHolderInstance_27.R = R;//####[2217]####
                _omp__parallelRegionVarHolderInstance_27.G = G;//####[2218]####
                _omp__parallelRegionVarHolderInstance_27.B = B;//####[2219]####
                _omp__parallelRegionVarHolderInstance_27.pixelColor = pixelColor;//####[2220]####
                _omp__parallelRegionVarHolderInstance_27.src = src;//####[2221]####
                _omp__parallelRegionVarHolderInstance_27.output = output;//####[2222]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[2225]####
                TaskID _omp__parallelRegionTaskID_27 = _ompParallelRegion_27(_omp__parallelRegionVarHolderInstance_27);//####[2226]####
                __pt___ompParallelRegion_27(_omp__parallelRegionVarHolderInstance_27);//####[2227]####
                try {//####[2228]####
                    _omp__parallelRegionTaskID_27.waitTillFinished();//####[2228]####
                } catch (Exception __pt__ex) {//####[2228]####
                    __pt__ex.printStackTrace();//####[2228]####
                }//####[2228]####
                PJPackageOnly.setMasterThread(null);//####[2230]####
                _holderForPIFirst.set(true);//####[2231]####
                width = _omp__parallelRegionVarHolderInstance_27.width;//####[2233]####
                height = _omp__parallelRegionVarHolderInstance_27.height;//####[2234]####
                A = _omp__parallelRegionVarHolderInstance_27.A;//####[2235]####
                R = _omp__parallelRegionVarHolderInstance_27.R;//####[2236]####
                G = _omp__parallelRegionVarHolderInstance_27.G;//####[2237]####
                B = _omp__parallelRegionVarHolderInstance_27.B;//####[2238]####
                pixelColor = _omp__parallelRegionVarHolderInstance_27.pixelColor;//####[2239]####
                src = _omp__parallelRegionVarHolderInstance_27.src;//####[2240]####
                output = _omp__parallelRegionVarHolderInstance_27.output;//####[2241]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[2242]####
            }//####[2243]####
            src.recycle();//####[2246]####
            src = null;//####[2247]####
            return output;//####[2248]####
        }//####[2249]####
    }//####[2250]####
//####[2251]####
    private static AtomicBoolean _imFirst_29 = new AtomicBoolean(true);//####[2251]####
//####[2252]####
    private static AtomicInteger _imFinishedCounter_29 = new AtomicInteger(0);//####[2252]####
//####[2253]####
    private static CountDownLatch _waitBarrier_29 = new CountDownLatch(1);//####[2253]####
//####[2254]####
    private static CountDownLatch _waitBarrierAfter_29 = new CountDownLatch(1);//####[2254]####
//####[2255]####
    private static ParIterator<Integer> _pi_29 = null;//####[2255]####
//####[2256]####
    private static Integer _lastElement_29 = null;//####[2256]####
//####[2257]####
    private static _ompWorkSharedUserCode_BitmapProcessing29_variables _ompWorkSharedUserCode_BitmapProcessing29_variables_instance = null;//####[2257]####
//####[2258]####
    private static void _ompWorkSharedUserCode_BitmapProcessing29(_ompWorkSharedUserCode_BitmapProcessing29_variables __omp_vars) {//####[2258]####
        int G = __omp_vars.G;//####[2260]####
        int A = __omp_vars.A;//####[2261]####
        int B = __omp_vars.B;//####[2262]####
        int width = __omp_vars.width;//####[2263]####
        int height = __omp_vars.height;//####[2264]####
        int R = __omp_vars.R;//####[2265]####
        int pixelColor = __omp_vars.pixelColor;//####[2266]####
        Bitmap src = __omp_vars.src;//####[2267]####
        Bitmap output = __omp_vars.output;//####[2268]####
        Integer y;//####[2269]####
        while (_pi_29.hasNext()) //####[2270]####
        {//####[2270]####
            y = _pi_29.next();//####[2271]####
            {//####[2273]####
                for (int x = 0; x < width; x++) //####[2274]####
                {//####[2274]####
                    pixelColor = src.getPixel(x, y);//####[2275]####
                    A = Color.alpha(pixelColor);//####[2276]####
                    R = 255 - Color.red(pixelColor);//####[2277]####
                    G = 255 - Color.green(pixelColor);//####[2278]####
                    B = 255 - Color.blue(pixelColor);//####[2279]####
                    output.setPixel(x, y, Color.argb(A, R, G, B));//####[2280]####
                }//####[2281]####
            }//####[2282]####
        }//####[2283]####
        __omp_vars.G = G;//####[2285]####
        __omp_vars.A = A;//####[2286]####
        __omp_vars.B = B;//####[2287]####
        __omp_vars.width = width;//####[2288]####
        __omp_vars.height = height;//####[2289]####
        __omp_vars.R = R;//####[2290]####
        __omp_vars.pixelColor = pixelColor;//####[2291]####
        __omp_vars.src = src;//####[2292]####
        __omp_vars.output = output;//####[2293]####
    }//####[2294]####
//####[2298]####
    private static volatile Method __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method = null;//####[2298]####
    private synchronized static void __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_ensureMethodVarSet() {//####[2298]####
        if (__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method == null) {//####[2298]####
            try {//####[2298]####
                __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_27", new Class[] {//####[2298]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing27.class//####[2298]####
                });//####[2298]####
            } catch (Exception e) {//####[2298]####
                e.printStackTrace();//####[2298]####
            }//####[2298]####
        }//####[2298]####
    }//####[2298]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(_omp__parallelRegionVarHolderClass_BitmapProcessing27 __omp_vars) {//####[2298]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2298]####
        return _ompParallelRegion_27(__omp_vars, new TaskInfo());//####[2298]####
    }//####[2298]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(_omp__parallelRegionVarHolderClass_BitmapProcessing27 __omp_vars, TaskInfo taskinfo) {//####[2298]####
        // ensure Method variable is set//####[2298]####
        if (__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method == null) {//####[2298]####
            __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_ensureMethodVarSet();//####[2298]####
        }//####[2298]####
        taskinfo.setParameters(__omp_vars);//####[2298]####
        taskinfo.setMethod(__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method);//####[2298]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2298]####
    }//####[2298]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing27> __omp_vars) {//####[2298]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2298]####
        return _ompParallelRegion_27(__omp_vars, new TaskInfo());//####[2298]####
    }//####[2298]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing27> __omp_vars, TaskInfo taskinfo) {//####[2298]####
        // ensure Method variable is set//####[2298]####
        if (__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method == null) {//####[2298]####
            __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_ensureMethodVarSet();//####[2298]####
        }//####[2298]####
        taskinfo.setTaskIdArgIndexes(0);//####[2298]####
        taskinfo.addDependsOn(__omp_vars);//####[2298]####
        taskinfo.setParameters(__omp_vars);//####[2298]####
        taskinfo.setMethod(__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method);//####[2298]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2298]####
    }//####[2298]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing27> __omp_vars) {//####[2298]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2298]####
        return _ompParallelRegion_27(__omp_vars, new TaskInfo());//####[2298]####
    }//####[2298]####
    private static TaskIDGroup<Void> _ompParallelRegion_27(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing27> __omp_vars, TaskInfo taskinfo) {//####[2298]####
        // ensure Method variable is set//####[2298]####
        if (__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method == null) {//####[2298]####
            __pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_ensureMethodVarSet();//####[2298]####
        }//####[2298]####
        taskinfo.setQueueArgIndexes(0);//####[2298]####
        taskinfo.setIsPipeline(true);//####[2298]####
        taskinfo.setParameters(__omp_vars);//####[2298]####
        taskinfo.setMethod(__pt___ompParallelRegion_27__omp__parallelRegionVarHolderClass_BitmapProcessing27_method);//####[2298]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2298]####
    }//####[2298]####
    public static void __pt___ompParallelRegion_27(_omp__parallelRegionVarHolderClass_BitmapProcessing27 __omp_vars) {//####[2298]####
        int width = __omp_vars.width;//####[2300]####
        int height = __omp_vars.height;//####[2301]####
        int A = __omp_vars.A;//####[2302]####
        int R = __omp_vars.R;//####[2303]####
        int G = __omp_vars.G;//####[2304]####
        int B = __omp_vars.B;//####[2305]####
        int pixelColor = __omp_vars.pixelColor;//####[2306]####
        Bitmap src = __omp_vars.src;//####[2307]####
        Bitmap output = __omp_vars.output;//####[2308]####
        {//####[2309]####
            if (Pyjama.insideParallelRegion()) //####[2310]####
            {//####[2310]####
                boolean _omp_imFirst = _imFirst_29.getAndSet(false);//####[2312]####
                _holderForPIFirst = _imFirst_29;//####[2313]####
                if (_omp_imFirst) //####[2314]####
                {//####[2314]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing29_variables();//####[2315]####
                    int __omp_size_ = 0;//####[2316]####
                    for (int y = 0; y < height; y = y + 1) //####[2318]####
                    {//####[2318]####
                        _lastElement_29 = y;//####[2319]####
                        __omp_size_++;//####[2320]####
                    }//####[2321]####
                    _pi_29 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[2322]####
                    _omp_piVarContainer.add(_pi_29);//####[2323]####
                    _pi_29.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[2324]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.G = G;//####[2325]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.A = A;//####[2326]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.B = B;//####[2327]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.width = width;//####[2328]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.height = height;//####[2329]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.R = R;//####[2330]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.pixelColor = pixelColor;//####[2331]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.src = src;//####[2332]####
                    _ompWorkSharedUserCode_BitmapProcessing29_variables_instance.output = output;//####[2333]####
                    _waitBarrier_29.countDown();//####[2334]####
                } else {//####[2335]####
                    try {//####[2336]####
                        _waitBarrier_29.await();//####[2336]####
                    } catch (InterruptedException __omp__ie) {//####[2336]####
                        __omp__ie.printStackTrace();//####[2336]####
                    }//####[2336]####
                }//####[2337]####
                _ompWorkSharedUserCode_BitmapProcessing29(_ompWorkSharedUserCode_BitmapProcessing29_variables_instance);//####[2338]####
                if (_imFinishedCounter_29.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[2339]####
                {//####[2339]####
                    _waitBarrierAfter_29.countDown();//####[2340]####
                } else {//####[2341]####
                    try {//####[2342]####
                        _waitBarrierAfter_29.await();//####[2343]####
                    } catch (InterruptedException __omp__ie) {//####[2344]####
                        __omp__ie.printStackTrace();//####[2345]####
                    }//####[2346]####
                }//####[2347]####
            } else {//####[2350]####
                for (int y = 0; y < height; y = y + 1) //####[2352]####
                {//####[2352]####
                    for (int x = 0; x < __omp_vars.width; x++) //####[2353]####
                    {//####[2353]####
                        __omp_vars.pixelColor = __omp_vars.src.getPixel(x, y);//####[2354]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixelColor);//####[2355]####
                        __omp_vars.R = 255 - Color.red(__omp_vars.pixelColor);//####[2356]####
                        __omp_vars.G = 255 - Color.green(__omp_vars.pixelColor);//####[2357]####
                        __omp_vars.B = 255 - Color.blue(__omp_vars.pixelColor);//####[2358]####
                        __omp_vars.output.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[2359]####
                    }//####[2360]####
                }//####[2361]####
            }//####[2362]####
        }//####[2364]####
        __omp_vars.src = src;//####[2366]####
        __omp_vars.output = output;//####[2367]####
    }//####[2368]####
//####[2368]####
//####[2369]####
    public static Bitmap boost(Bitmap src, int type, float percent) {//####[2369]####
        {//####[2369]####
            percent = (float) percent / 100;//####[2370]####
            int width = src.getWidth();//####[2371]####
            int height = src.getHeight();//####[2372]####
            Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());//####[2373]####
            int A = 0, R = 0, G = 0, B = 0;//####[2374]####
            int pixel = 0;//####[2375]####
            if (Pyjama.insideParallelRegion()) //####[2377]####
            {//####[2377]####
                {//####[2379]####
                    for (int x = 0; x < width; x = x + 1) //####[2380]####
                    {//####[2380]####
                        for (int y = 0; y < height; ++y) //####[2381]####
                        {//####[2381]####
                            pixel = src.getPixel(x, y);//####[2382]####
                            A = Color.alpha(pixel);//####[2383]####
                            R = Color.red(pixel);//####[2384]####
                            G = Color.green(pixel);//####[2385]####
                            B = Color.blue(pixel);//####[2386]####
                            if (type == 1) //####[2387]####
                            {//####[2387]####
                                R = (int) (R * (1 + percent));//####[2388]####
                                if (R > 255) //####[2389]####
                                R = 255;//####[2389]####
                            } else if (type == 2) //####[2390]####
                            {//####[2390]####
                                G = (int) (G * (1 + percent));//####[2391]####
                                if (G > 255) //####[2392]####
                                G = 255;//####[2392]####
                            } else if (type == 3) //####[2393]####
                            {//####[2393]####
                                B = (int) (B * (1 + percent));//####[2394]####
                                if (B > 255) //####[2395]####
                                B = 255;//####[2395]####
                            }//####[2396]####
                            bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[2397]####
                        }//####[2398]####
                    }//####[2399]####
                }//####[2400]####
            } else {//####[2401]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[2403]####
                _omp__parallelRegionVarHolderClass_BitmapProcessing30 _omp__parallelRegionVarHolderInstance_30 = new _omp__parallelRegionVarHolderClass_BitmapProcessing30();//####[2406]####
                _omp__parallelRegionVarHolderInstance_30.width = width;//####[2409]####
                _omp__parallelRegionVarHolderInstance_30.height = height;//####[2410]####
                _omp__parallelRegionVarHolderInstance_30.A = A;//####[2411]####
                _omp__parallelRegionVarHolderInstance_30.R = R;//####[2412]####
                _omp__parallelRegionVarHolderInstance_30.G = G;//####[2413]####
                _omp__parallelRegionVarHolderInstance_30.B = B;//####[2414]####
                _omp__parallelRegionVarHolderInstance_30.pixel = pixel;//####[2415]####
                _omp__parallelRegionVarHolderInstance_30.type = type;//####[2416]####
                _omp__parallelRegionVarHolderInstance_30.percent = percent;//####[2417]####
                _omp__parallelRegionVarHolderInstance_30.bmOut = bmOut;//####[2418]####
                _omp__parallelRegionVarHolderInstance_30.src = src;//####[2419]####
                PJPackageOnly.setMasterThread(Thread.currentThread());//####[2422]####
                TaskID _omp__parallelRegionTaskID_30 = _ompParallelRegion_30(_omp__parallelRegionVarHolderInstance_30);//####[2423]####
                __pt___ompParallelRegion_30(_omp__parallelRegionVarHolderInstance_30);//####[2424]####
                try {//####[2425]####
                    _omp__parallelRegionTaskID_30.waitTillFinished();//####[2425]####
                } catch (Exception __pt__ex) {//####[2425]####
                    __pt__ex.printStackTrace();//####[2425]####
                }//####[2425]####
                PJPackageOnly.setMasterThread(null);//####[2427]####
                _holderForPIFirst.set(true);//####[2428]####
                width = _omp__parallelRegionVarHolderInstance_30.width;//####[2430]####
                height = _omp__parallelRegionVarHolderInstance_30.height;//####[2431]####
                A = _omp__parallelRegionVarHolderInstance_30.A;//####[2432]####
                R = _omp__parallelRegionVarHolderInstance_30.R;//####[2433]####
                G = _omp__parallelRegionVarHolderInstance_30.G;//####[2434]####
                B = _omp__parallelRegionVarHolderInstance_30.B;//####[2435]####
                pixel = _omp__parallelRegionVarHolderInstance_30.pixel;//####[2436]####
                type = _omp__parallelRegionVarHolderInstance_30.type;//####[2437]####
                percent = _omp__parallelRegionVarHolderInstance_30.percent;//####[2438]####
                bmOut = _omp__parallelRegionVarHolderInstance_30.bmOut;//####[2439]####
                src = _omp__parallelRegionVarHolderInstance_30.src;//####[2440]####
                PJPackageOnly.setThreadCountCurrentParallelRegion(1);//####[2441]####
            }//####[2442]####
            src.recycle();//####[2445]####
            src = null;//####[2446]####
            return bmOut;//####[2447]####
        }//####[2448]####
    }//####[2449]####
//####[2450]####
    private static AtomicBoolean _imFirst_32 = new AtomicBoolean(true);//####[2450]####
//####[2451]####
    private static AtomicInteger _imFinishedCounter_32 = new AtomicInteger(0);//####[2451]####
//####[2452]####
    private static CountDownLatch _waitBarrier_32 = new CountDownLatch(1);//####[2452]####
//####[2453]####
    private static CountDownLatch _waitBarrierAfter_32 = new CountDownLatch(1);//####[2453]####
//####[2454]####
    private static ParIterator<Integer> _pi_32 = null;//####[2454]####
//####[2455]####
    private static Integer _lastElement_32 = null;//####[2455]####
//####[2456]####
    private static _ompWorkSharedUserCode_BitmapProcessing32_variables _ompWorkSharedUserCode_BitmapProcessing32_variables_instance = null;//####[2456]####
//####[2457]####
    private static void _ompWorkSharedUserCode_BitmapProcessing32(_ompWorkSharedUserCode_BitmapProcessing32_variables __omp_vars) {//####[2457]####
        int G = __omp_vars.G;//####[2459]####
        float percent = __omp_vars.percent;//####[2460]####
        int pixel = __omp_vars.pixel;//####[2461]####
        int A = __omp_vars.A;//####[2462]####
        int B = __omp_vars.B;//####[2463]####
        int width = __omp_vars.width;//####[2464]####
        int type = __omp_vars.type;//####[2465]####
        Bitmap bmOut = __omp_vars.bmOut;//####[2466]####
        int height = __omp_vars.height;//####[2467]####
        int R = __omp_vars.R;//####[2468]####
        Bitmap src = __omp_vars.src;//####[2469]####
        Integer x;//####[2470]####
        while (_pi_32.hasNext()) //####[2471]####
        {//####[2471]####
            x = _pi_32.next();//####[2472]####
            {//####[2474]####
                for (int y = 0; y < height; ++y) //####[2475]####
                {//####[2475]####
                    pixel = src.getPixel(x, y);//####[2476]####
                    A = Color.alpha(pixel);//####[2477]####
                    R = Color.red(pixel);//####[2478]####
                    G = Color.green(pixel);//####[2479]####
                    B = Color.blue(pixel);//####[2480]####
                    if (type == 1) //####[2481]####
                    {//####[2481]####
                        R = (int) (R * (1 + percent));//####[2482]####
                        if (R > 255) //####[2483]####
                        R = 255;//####[2483]####
                    } else if (type == 2) //####[2484]####
                    {//####[2484]####
                        G = (int) (G * (1 + percent));//####[2485]####
                        if (G > 255) //####[2486]####
                        G = 255;//####[2486]####
                    } else if (type == 3) //####[2487]####
                    {//####[2487]####
                        B = (int) (B * (1 + percent));//####[2488]####
                        if (B > 255) //####[2489]####
                        B = 255;//####[2489]####
                    }//####[2490]####
                    bmOut.setPixel(x, y, Color.argb(A, R, G, B));//####[2491]####
                }//####[2492]####
            }//####[2493]####
        }//####[2494]####
        __omp_vars.G = G;//####[2496]####
        __omp_vars.percent = percent;//####[2497]####
        __omp_vars.pixel = pixel;//####[2498]####
        __omp_vars.A = A;//####[2499]####
        __omp_vars.B = B;//####[2500]####
        __omp_vars.width = width;//####[2501]####
        __omp_vars.type = type;//####[2502]####
        __omp_vars.bmOut = bmOut;//####[2503]####
        __omp_vars.height = height;//####[2504]####
        __omp_vars.R = R;//####[2505]####
        __omp_vars.src = src;//####[2506]####
    }//####[2507]####
//####[2511]####
    private static volatile Method __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method = null;//####[2511]####
    private synchronized static void __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_ensureMethodVarSet() {//####[2511]####
        if (__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method == null) {//####[2511]####
            try {//####[2511]####
                __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_30", new Class[] {//####[2511]####
                    _omp__parallelRegionVarHolderClass_BitmapProcessing30.class//####[2511]####
                });//####[2511]####
            } catch (Exception e) {//####[2511]####
                e.printStackTrace();//####[2511]####
            }//####[2511]####
        }//####[2511]####
    }//####[2511]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(_omp__parallelRegionVarHolderClass_BitmapProcessing30 __omp_vars) {//####[2511]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2511]####
        return _ompParallelRegion_30(__omp_vars, new TaskInfo());//####[2511]####
    }//####[2511]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(_omp__parallelRegionVarHolderClass_BitmapProcessing30 __omp_vars, TaskInfo taskinfo) {//####[2511]####
        // ensure Method variable is set//####[2511]####
        if (__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method == null) {//####[2511]####
            __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_ensureMethodVarSet();//####[2511]####
        }//####[2511]####
        taskinfo.setParameters(__omp_vars);//####[2511]####
        taskinfo.setMethod(__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method);//####[2511]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2511]####
    }//####[2511]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing30> __omp_vars) {//####[2511]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2511]####
        return _ompParallelRegion_30(__omp_vars, new TaskInfo());//####[2511]####
    }//####[2511]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(TaskID<_omp__parallelRegionVarHolderClass_BitmapProcessing30> __omp_vars, TaskInfo taskinfo) {//####[2511]####
        // ensure Method variable is set//####[2511]####
        if (__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method == null) {//####[2511]####
            __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_ensureMethodVarSet();//####[2511]####
        }//####[2511]####
        taskinfo.setTaskIdArgIndexes(0);//####[2511]####
        taskinfo.addDependsOn(__omp_vars);//####[2511]####
        taskinfo.setParameters(__omp_vars);//####[2511]####
        taskinfo.setMethod(__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method);//####[2511]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2511]####
    }//####[2511]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing30> __omp_vars) {//####[2511]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[2511]####
        return _ompParallelRegion_30(__omp_vars, new TaskInfo());//####[2511]####
    }//####[2511]####
    private static TaskIDGroup<Void> _ompParallelRegion_30(BlockingQueue<_omp__parallelRegionVarHolderClass_BitmapProcessing30> __omp_vars, TaskInfo taskinfo) {//####[2511]####
        // ensure Method variable is set//####[2511]####
        if (__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method == null) {//####[2511]####
            __pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_ensureMethodVarSet();//####[2511]####
        }//####[2511]####
        taskinfo.setQueueArgIndexes(0);//####[2511]####
        taskinfo.setIsPipeline(true);//####[2511]####
        taskinfo.setParameters(__omp_vars);//####[2511]####
        taskinfo.setMethod(__pt___ompParallelRegion_30__omp__parallelRegionVarHolderClass_BitmapProcessing30_method);//####[2511]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[2511]####
    }//####[2511]####
    public static void __pt___ompParallelRegion_30(_omp__parallelRegionVarHolderClass_BitmapProcessing30 __omp_vars) {//####[2511]####
        int width = __omp_vars.width;//####[2513]####
        int height = __omp_vars.height;//####[2514]####
        int A = __omp_vars.A;//####[2515]####
        int R = __omp_vars.R;//####[2516]####
        int G = __omp_vars.G;//####[2517]####
        int B = __omp_vars.B;//####[2518]####
        int pixel = __omp_vars.pixel;//####[2519]####
        int type = __omp_vars.type;//####[2520]####
        float percent = __omp_vars.percent;//####[2521]####
        Bitmap bmOut = __omp_vars.bmOut;//####[2522]####
        Bitmap src = __omp_vars.src;//####[2523]####
        {//####[2524]####
            if (Pyjama.insideParallelRegion()) //####[2525]####
            {//####[2525]####
                boolean _omp_imFirst = _imFirst_32.getAndSet(false);//####[2527]####
                _holderForPIFirst = _imFirst_32;//####[2528]####
                if (_omp_imFirst) //####[2529]####
                {//####[2529]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance = new _ompWorkSharedUserCode_BitmapProcessing32_variables();//####[2530]####
                    int __omp_size_ = 0;//####[2531]####
                    for (int x = 0; x < width; x = x + 1) //####[2533]####
                    {//####[2533]####
                        _lastElement_32 = x;//####[2534]####
                        __omp_size_++;//####[2535]####
                    }//####[2536]####
                    _pi_32 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[2537]####
                    _omp_piVarContainer.add(_pi_32);//####[2538]####
                    _pi_32.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[2539]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.G = G;//####[2540]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.percent = percent;//####[2541]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.pixel = pixel;//####[2542]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.A = A;//####[2543]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.B = B;//####[2544]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.width = width;//####[2545]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.type = type;//####[2546]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.bmOut = bmOut;//####[2547]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.height = height;//####[2548]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.R = R;//####[2549]####
                    _ompWorkSharedUserCode_BitmapProcessing32_variables_instance.src = src;//####[2550]####
                    _waitBarrier_32.countDown();//####[2551]####
                } else {//####[2552]####
                    try {//####[2553]####
                        _waitBarrier_32.await();//####[2553]####
                    } catch (InterruptedException __omp__ie) {//####[2553]####
                        __omp__ie.printStackTrace();//####[2553]####
                    }//####[2553]####
                }//####[2554]####
                _ompWorkSharedUserCode_BitmapProcessing32(_ompWorkSharedUserCode_BitmapProcessing32_variables_instance);//####[2555]####
                if (_imFinishedCounter_32.incrementAndGet() == PJPackageOnly.getThreadCountCurrentParallelRegion()) //####[2556]####
                {//####[2556]####
                    _waitBarrierAfter_32.countDown();//####[2557]####
                } else {//####[2558]####
                    try {//####[2559]####
                        _waitBarrierAfter_32.await();//####[2560]####
                    } catch (InterruptedException __omp__ie) {//####[2561]####
                        __omp__ie.printStackTrace();//####[2562]####
                    }//####[2563]####
                }//####[2564]####
            } else {//####[2567]####
                for (int x = 0; x < width; x = x + 1) //####[2569]####
                {//####[2569]####
                    for (int y = 0; y < __omp_vars.height; ++y) //####[2570]####
                    {//####[2570]####
                        __omp_vars.pixel = __omp_vars.src.getPixel(x, y);//####[2571]####
                        __omp_vars.A = Color.alpha(__omp_vars.pixel);//####[2572]####
                        __omp_vars.R = Color.red(__omp_vars.pixel);//####[2573]####
                        __omp_vars.G = Color.green(__omp_vars.pixel);//####[2574]####
                        __omp_vars.B = Color.blue(__omp_vars.pixel);//####[2575]####
                        if (__omp_vars.type == 1) //####[2576]####
                        {//####[2576]####
                            __omp_vars.R = (int) (__omp_vars.R * (1 + __omp_vars.percent));//####[2577]####
                            if (__omp_vars.R > 255) //####[2578]####
                            __omp_vars.R = 255;//####[2578]####
                        } else if (__omp_vars.type == 2) //####[2579]####
                        {//####[2579]####
                            __omp_vars.G = (int) (__omp_vars.G * (1 + __omp_vars.percent));//####[2580]####
                            if (__omp_vars.G > 255) //####[2581]####
                            __omp_vars.G = 255;//####[2581]####
                        } else if (__omp_vars.type == 3) //####[2582]####
                        {//####[2582]####
                            __omp_vars.B = (int) (__omp_vars.B * (1 + __omp_vars.percent));//####[2583]####
                            if (__omp_vars.B > 255) //####[2584]####
                            __omp_vars.B = 255;//####[2584]####
                        }//####[2585]####
                        __omp_vars.bmOut.setPixel(x, y, Color.argb(__omp_vars.A, __omp_vars.R, __omp_vars.G, __omp_vars.B));//####[2586]####
                    }//####[2587]####
                }//####[2588]####
            }//####[2589]####
        }//####[2591]####
        __omp_vars.percent = percent;//####[2593]####
        __omp_vars.bmOut = bmOut;//####[2594]####
        __omp_vars.src = src;//####[2595]####
    }//####[2596]####
//####[2596]####
//####[2597]####
    public static final Bitmap sketch(Bitmap src) {//####[2597]####
        {//####[2597]####
            int type = 6;//####[2598]####
            int threshold = 130;//####[2599]####
            int width = src.getWidth();//####[2600]####
            int height = src.getHeight();//####[2601]####
            Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());//####[2602]####
            int A = 0, R = 0, G = 0, B = 0;//####[2603]####
            int sumR = 0, sumG = 0, sumB = 0;//####[2604]####
            int[][] pixels = new int[3][3];//####[2605]####
            for (int y = 0; y < height - 2; ++y) //####[2606]####
            {//####[2606]####
                for (int x = 0; x < width - 2; ++x) //####[2607]####
                {//####[2607]####
                    for (int i = 0; i < 3; ++i) //####[2608]####
                    {//####[2608]####
                        for (int j = 0; j < 3; ++j) //####[2609]####
                        {//####[2609]####
                            pixels[i][j] = src.getPixel(x + i, y + j);//####[2610]####
                        }//####[2611]####
                    }//####[2612]####
                    A = Color.alpha(pixels[1][1]);//####[2613]####
                    sumR = sumG = sumB = 0;//####[2614]####
                    sumR = (type * Color.red(pixels[1][1])) - Color.red(pixels[0][0]) - Color.red(pixels[0][2]) - Color.red(pixels[2][0]) - Color.red(pixels[2][2]);//####[2615]####
                    sumG = (type * Color.green(pixels[1][1])) - Color.green(pixels[0][0]) - Color.green(pixels[0][2]) - Color.green(pixels[2][0]) - Color.green(pixels[2][2]);//####[2616]####
                    sumB = (type * Color.blue(pixels[1][1])) - Color.blue(pixels[0][0]) - Color.blue(pixels[0][2]) - Color.blue(pixels[2][0]) - Color.blue(pixels[2][2]);//####[2617]####
                    R = (int) (sumR + threshold);//####[2618]####
                    if (R < 0) //####[2619]####
                    {//####[2619]####
                        R = 0;//####[2620]####
                    } else if (R > 255) //####[2621]####
                    {//####[2621]####
                        R = 255;//####[2622]####
                    }//####[2623]####
                    G = (int) (sumG + threshold);//####[2624]####
                    if (G < 0) //####[2625]####
                    {//####[2625]####
                        G = 0;//####[2626]####
                    } else if (G > 255) //####[2627]####
                    {//####[2627]####
                        G = 255;//####[2628]####
                    }//####[2629]####
                    B = (int) (sumB + threshold);//####[2630]####
                    if (B < 0) //####[2631]####
                    {//####[2631]####
                        B = 0;//####[2632]####
                    } else if (B > 255) //####[2633]####
                    {//####[2633]####
                        B = 255;//####[2634]####
                    }//####[2635]####
                    result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));//####[2636]####
                }//####[2637]####
            }//####[2638]####
            src.recycle();//####[2639]####
            src = null;//####[2640]####
            return result;//####[2641]####
        }//####[2642]####
    }//####[2643]####
//####[2645]####
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_url) throws IOException {//####[2645]####
        {//####[2645]####
            ExifInterface ei = new ExifInterface(image_url);//####[2646]####
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);//####[2647]####
            switch(orientation) {//####[2648]####
                case ExifInterface.ORIENTATION_ROTATE_90://####[2648]####
                    return rotate(bitmap, 90);//####[2650]####
                case ExifInterface.ORIENTATION_ROTATE_180://####[2650]####
                    return rotate(bitmap, 180);//####[2652]####
                case ExifInterface.ORIENTATION_ROTATE_270://####[2652]####
                    return rotate(bitmap, 270);//####[2654]####
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL://####[2654]####
                    return flip(bitmap, true, false);//####[2656]####
                case ExifInterface.ORIENTATION_FLIP_VERTICAL://####[2656]####
                    return flip(bitmap, false, true);//####[2658]####
                default://####[2658]####
                    return bitmap;//####[2660]####
            }//####[2660]####
        }//####[2662]####
    }//####[2663]####
}//####[2663]####
