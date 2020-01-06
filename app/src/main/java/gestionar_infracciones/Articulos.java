package gestionar_infracciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ucot.R;

import Modelos.Articulos_Coip;
import Utilidades.Constantes;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;

public class Articulos extends AppCompatActivity {
    HintSpinner hintSpinnerarticulo, hintSpinnerinciso, hintSpinnernumeral;
    private static final String PREF_NAME = "datos";
    String art;
    String inc;
    String num;
    int a, i, n;
    EditText descripcion;
    TextView articulo1, inciso1, numeral1;

    //Articulos
    private CustomObject[] COIPA = new CustomObject[]{
            new CustomObject("Artículo 383.- Conducción de vehículo con llantas en mal estado."),
            new CustomObject("Artículo 384.- Conducción de vehículo bajo efecto de sustancias estupefacientes, psicotrópicas o preparados que las contengan."),
            new CustomObject("Artículo 385.- Conducción de vehículo en estado de embriaguez."),
            new CustomObject("Artículo 386.- Contravenciones de tránsito de primera clase."),
            new CustomObject("Artículo 387.- Contravenciones de tránsito de segunda clase."),
            new CustomObject("Artículo 388.- Contravenciones de tránsito de tercera clase."),
            new CustomObject( "Artículo 389.- Contravenciones de tránsito de cuarta clase."),
            new CustomObject("Artículo 390.- Contravenciones de tránsito de quinta clase."),
            new CustomObject("Artículo 391.- Contravenciones de tránsito de sexta clase."),
            new CustomObject("Artículo 392.- Contravenciones de tránsito de séptima clase."),
    };

    private boolean validar() {
        boolean band = true;
        if (articulo1.getText ().toString ().isEmpty ()){
            articulo1.setError ("Campo requrrido");
            band = false;
        }
        if (inciso1.getText ().toString ().isEmpty ()){
            inciso1.setError ("Campo requrrido");
            band = false;
        }
        return band;
    }

    //Inciso de acuerdo al articulo seleccionado
    //Articulo 383
    private CustomObject[] A383 = new CustomObject[]{
            new CustomObject("1. La persona que conduzca un vehículo cuyas llantas se encuentren lisas o en mal estado, será sancionada con pena privativa de libertad de cinco a quince días y disminución de cinco puntos en " +
                    "la licencia de conducir."),
            new CustomObject("2. En caso de transporte público, la pena será el doble de la prevista en el inciso anterior."),
    };

    //Articulo 384
    private CustomObject[] A384 = new CustomObject[]{
            new CustomObject("1. La persona que conduzca un vehículo bajo los efectos de sustancias estupefacientes, psicotrópicas o preparados que las contengan, será sancionada " +
                    "con reducción de quince puntos de su licencia de conducir y treinta días de privación de libertad; además como medida preventiva se aprehenderá el vehículo por " +
                    "veinticuatro horas."),
    };

    //Articulo 385
    private CustomObject[] A385 = new CustomObject[]{
            new CustomObject("1. La persona que conduzca un vehículo en estado de embriaguez, será sancionada de acuerdo con la siguiente escala:"),
            new CustomObject("2. Para las o los conductores de vehículos de transporte público liviano o pesado, comercial o de carga, la tolerancia al consumo de cualquier sustancia estupefaciente, psicotrópica o preparado que las " +
                    "contengan es cero, y un nivel máximo de alcohol de 0,1 gramos por cada litro de sangre. En caso de exceder dicho límite, la sanción para el responsable será, pérdida de treinta puntos en su " +
                    "licencia de conducir y pena privativa de libertad de noventa días."),
    };

    //Articulo 386
    private CustomObject[] A386 = new CustomObject[]{
            new CustomObject("1. Será sancionado con pena privativa de libertad de tres días, multa de un salario básico unificado del trabajador en general y reducción de diez puntos en su licencia de conducir:"),
            new CustomObject("2. Será sancionado con dos salarios básicos unificados del trabajador en general, reducción de diez puntos en su licencia de conducir y retención del vehículo por el plazo mínimo de siete días:"),
    };

    //Articulo 387
    private CustomObject[] A387 = new CustomObject[]{
            new CustomObject("1. Serán sancionados con multa del cincuenta por ciento de un salario básico unificado del trabajador en general y reducción de nueve puntos en el registro de su licencia de conducir:"),
    };

    //Articulo 388
    private CustomObject[] A388 = new CustomObject[]{
            new CustomObject("1. Serán sancionados con multa equivalente al cuarenta por ciento de un salario básico unificado del trabajador en general y reducción de siete punto cinco puntos en su licencia de conducir:"),
    };

    //Articulo 389
    private CustomObject[] A389 = new CustomObject[]{
            new CustomObject("1. Serán sancionados con multa equivalente al treinta por ciento de un salario básico unificado del trabajador en general, y reducción de seis puntos en su licencia de conducir:"),
    };

    //Articulo 390
    private CustomObject[] A390 = new CustomObject[]{
            new CustomObject("1. Será sancionado con multa equivalente al quince por ciento de un salario básico unificado del trabajador en general y reducción de cuatro punto cinco puntos en su licencia de conducir:"),
    };

    //Articulo 391
    private CustomObject[] A391 = new CustomObject[]{
            new CustomObject("1. Será sancionado con multa equivalente al diez por ciento de un salario básico unificado del trabajador general y reducción de tres puntos en su licencia de conducir:"),
    };

    //Articulo 392
    private CustomObject[] A392 = new CustomObject[]{
            new CustomObject("1. Será sancionado con multa equivalente al cinco por ciento de un salario básico unificado del trabajador general y reducción de uno punto cinco puntos en su licencia de conducir:"),
    };

    //Numeral de acuerdo a los articulos
    //Inciso 1 articulo 385 numeral
    private CustomObject[] A385N1 = new CustomObject[]{
            new CustomObject("1. Si el nivel de alcohol por litro de sangre es de 0,3 a 0,8 gramos, se aplicará multa de un salario básico unificado del trabajador en general, pérdida de cinco puntos en su " +
                    "licencia de conducir y cinco días de privación de libertad."),
            new CustomObject("2. Si el nivel de alcohol por litro de sangre es mayor de 0,8 hasta 1,2 gramos, se aplicará multa de dos salarios básicos unificados del trabajador en general, pérdida de diez puntos " +
                    "en su licencia de conducir y quince días de privación de libertad."),
            new CustomObject("3. Si el nivel de alcohol por litro de sangre supera 1,2 gramos, se aplicará multa de tres salarios básicos unificados del trabajador en general, la suspensión de la licencia " +
                    "por sesenta días y treinta días de privación de libertad."),
    };

    // Inciso 1 articulo 386 numeral
    private CustomObject[] A386N1 = new CustomObject[]{
            new CustomObject("1. La persona que conduzca sin haber obtenido licencia."),
            new CustomObject("2. La o el conductor que falte de obra a la autoridad o agente de tránsito."),
            new CustomObject("3. La o el conductor que con un vehículo automotor, exceda los límites de velocidad fuera del rango moderado, establecidos en el reglamento correspondiente."),
    };

    // Inciso 2 articulo 386 numeral
    private CustomObject[] A386N2 = new CustomObject[]{
            new CustomObject("1. La o el conductor que transporte pasajeros o bienes, sin contar con el título habilitante correspondiente, la autorización de frecuencia o que realice un servicio diferente " +
                    "para el que fue autorizado. Si además el vehículo ha sido pintado ilegalmente con el mismo color y características de los vehículos autorizados, la o el juzgador dispondrá " +
                    "que el vehículo sea pintado con un color distinto al de las unidades de transporte público o comercial y prohibirá su circulación, hasta tanto se cumpla con dicho mandamiento." +
                    "El cumplimiento de esta orden solo será probado con la certificación que para el efecto extenderá el responsable del sitio de retención vehicular al que será trasladado " +
                    "el vehículo no autorizado. Los costos del cambio de pintura del vehículo estarán a cargo de la persona contraventora."),
            new CustomObject("2. La persona que conduzca un vehículo con una licencia de categoría diferente a la exigible para el tipo de vehículo que conduce."),
            new CustomObject("3. Las personas que participen con vehículos a motor en competencias en la vía pública."),
    };

    // Inciso 1 articulo 387 numeral
    private CustomObject[] A387N1 = new CustomObject[]{
            new CustomObject("1. La o el conductor que ocasione un accidente de tránsito del que resulten solamente daños materiales, cuyos costos sean inferiores a dos salarios básicos unificados " +
                    "del trabajador en general."),
            new CustomObject("2. La persona que conduzca con licencia caducada, anulada, revocada o suspendida, la misma que deberá ser retirada inmediatamente por el agente de tránsito."),
            new CustomObject("3. La persona adolescente, mayor a dieciséis años, que posea un permiso de conducción que requiera compañía de un adulto que posea licencia y no cumpla con lo normado."),
            new CustomObject("4. La o el conductor extranjero que habiendo ingresado legalmente al país se encuentre brindando servicio de transporte comercial dentro de las zonas de frontera"),
            new CustomObject("5. La o el conductor de transporte por cuenta propia o comercial que exceda el número de pasajeros o volumen de carga de capacidad del automotor."),
    };

    // Inciso 1 articulo 388 numeral
    private CustomObject[] A388N1 = new CustomObject[]{
            new CustomObject("1. La o el conductor que detengan o estacionen vehículos en sitios o zonas que entrañen peligro, tales como: zonas de seguridad, curvas, puentes, ingresos y salidas de los mismos, " +
                    "túneles, así como el ingreso y salida de estos, zonas estrechas, de poca visibilidad, cruces de caminos, cambios de rasante, pendientes, o pasos a desnivel, sin tomar " +
                    "las medidas de seguridad señaladas en los reglamentos."),
            new CustomObject("2. La o el conductor que con un vehículo automotor o con los bienes que transporta, cause daños o deterioro a la superficie de la vía pública."),
            new CustomObject("3. La o el conductor que derrame en la vía pública sustancias o materiales deslizantes, inflamables o contaminantes, salvo caso fortuito o fuerza mayor debidamente comprobados."),
            new CustomObject("4. La o el conductor que transporte material inflamable, explosivo o peligroso en vehículos no acondicionados para el efecto o sin el permiso de la autoridad competente y las o " +
                    "los conductores no profesionales que realizaren esta actividad con un vehículo calificado para el efecto."),
            new CustomObject("5. La persona que construya o mande a construir reductores de velocidad sobre la calzada de las vías, sin previa autorización o inobservando las disposiciones de los respectivos reglamentos."),
            new CustomObject("6. Las personas que roturen o dañen las vías de circulación vehicular sin la respectiva autorización, dejen escombros o no retiren los desperdicios de la vía pública luego de " +
                    "terminadas las obras."),
            new CustomObject("7. La o el conductor de un vehículo automotor que circule con personas en los estribos o pisaderas, baldes de camionetas, parachoques o colgados de las carrocerías de los vehículos."),
            new CustomObject("8. La o el conductor de transporte público, comercial o independiente que realice el servicio de transporte de pasajeros y carga en cuyo vehículo no porte las franjas retroreflectivas " +
                    "previstas en los reglamentos de tránsito."),
            new CustomObject("9. La o el conductor de transporte público o comercial que se niegue a brindar el servicio."),
    };

    // Inciso 1 articulo 389 numeral
    private CustomObject[] A389N1 = new CustomObject[]{
            new CustomObject("1. La o el conductor que desobedezca las órdenes de los agentes de tránsito, o que no respete las señales manuales de dichos agentes, en general toda señalización colocada " +
                    "en las vías públicas, tales como: semáforos, pare, ceda el paso, cruce o preferencia de vías."),
            new CustomObject("2. La persona que adelante a otro vehículo en movimiento en zonas o sitios peligrosos, tales como: curvas, puentes, túneles, al coronar una cuesta o contraviniendo expresas " +
                    "normas reglamentarias o de señalización."),
            new CustomObject("3. La o el conductor que altere la circulación y la seguridad del tránsito vehicular, por colocar obstáculos en la vía pública sin la respectiva autorización o sin fijar los avisos " +
                    "correspondientes."),
            new CustomObject("4. Las o los conductores de vehículos de transporte escolar que no porten elementos distintivos y luces especiales de parqueo, que reglamentariamente deben ser utilizadas en las " +
                    "paradas para embarcar o desembarcar estudiantes."),
            new CustomObject("5. La o el conductor que falte de palabra a la autoridad o agente de tránsito."),
            new CustomObject("6. La o el conductor que con un vehículo automotor exceda dentro de un rango moderado los límites de velocidad permitidos, de conformidad con los reglamentos de tránsito correspondientes."),
            new CustomObject("7. La o el conductor que conduzca un vehículo a motor que no cumpla las normas y condiciones técnico mecánicas adecuadas conforme lo establezcan los reglamentos de tránsito respectivos, " +
                    "debiendo además retenerse el vehículo hasta que supere la causa de la infracción."),
            new CustomObject("8. La o el conductor profesional que sin autorización, preste servicio de transporte público, comercial, o por cuenta propia fuera del ámbito geográfico de prestación autorizada en el " +
                    "título habilitante correspondiente; se exceptúa el conductor de taxi fletado o de transporte mixto fletado que excepcionalmente transporte pasajeros fuera del ámbito de operación, " +
                    "quedando prohibido establecer rutas y frecuencias."),
            new CustomObject("9. La o el propietario de un automotor de servicio público, comercial o privado que confíe su conducción a personas no autorizadas."),
            new CustomObject("10. La o el conductor que transporte carga sin colocar en los extremos sobresalientes de la misma, banderines rojos en el día o luces en la noche, de acuerdo con lo establecido en " +
                    "los reglamentos de tránsito o sin observar los requisitos exigidos en los mismos."),
            new CustomObject("11. La o el conductor y los acompañantes, en caso de haberlos, de motocicletas, motonetas, bicimotos, tricar y cuadrones que no utilicen adecuadamente casco de seguridad homologados " +
                    "de conformidad con lo establecido en los reglamentos de tránsito o, que en la noche no utilicen prendas visibles retroreflectivas."),
            new CustomObject("12. La persona que conduzca un vehículo automotor sin las placas de identificación correspondientes o con las placas alteradas u ocultas y de conformidad con lo establecido en " +
                    "los reglamentos de tránsito."),
    };

    // Inciso 1 articulo 390 numeral
    private CustomObject[] A390N1 = new CustomObject[]{
            new CustomObject("1. La o el conductor que, al descender por una pendiente, apague el motor de su vehículo."),
            new CustomObject("2. La o el conductor que realice cualquier acción ilícita para evadir el pago de los peajes en los sitios legalmente establecidos."),
            new CustomObject("3. La o el conductor que conduzca un vehículo en sentido contrario a la vía normal de circulación, siempre que la respectiva señalización esté clara y visible."),
            new CustomObject("4. La o el conductor de un vehículo a diesel cuyo tubo de escape no esté instalado de conformidad con los reglamentos de tránsito."),
            new CustomObject("5. La o el propietario o conductor de un vehículo automotor que, en caso de emergencia o calamidad pública, luego de ser requeridos, se niegue a prestar la ayuda solicitada."),
            new CustomObject("6. La o el conductor de vehículos a motor que, ante las señales de alarma o toque de sirena de un vehículo de emergencia, no deje la vía libre."),
            new CustomObject("7. La o el conductor que detenga o estacione un vehículo automotor en lugares no permitidos, para dejar o recoger pasajeros o carga, o por cualquier otro motivo."),
            new CustomObject("8. La o el conductor que estacione un vehículo automotor en cualquier tipo de vías, sin tomar las precauciones reglamentariamente previstas para evitar un accidente de tránsito o lo " +
                    "deje abandonado en la vía pública."),
            new CustomObject("9. La o el conductor de un taxi, que no utilice el taxímetro las veinticuatro horas, altere su\n funcionamiento o no lo ubique en un lugar visible al usuario."),
            new CustomObject("10. La o el conductor de un vehículo automotor que tenga, según los reglamentos de tránsito, la obligación de contar con cinturones de seguridad y no exija el uso del mismo a sus " +
                    "usuarios o acompañantes."),
            new CustomObject("11. La o el conductor que haga cambio brusco o indebido de carril."),
            new CustomObject("12. La o el conductor de un vehículo de transporte público masivo de pasajeros que cargue combustible cuando se encuentren prestando el servicio de transporte."),
            new CustomObject("13. La o el conductor que lleve en sus brazos o en sitios no adecuados a personas, animales u objetos."),
            new CustomObject("14. La o el conductor que conduzca un vehículo sin luces, en mal estado de funcionamiento, no realice el cambio de las mismas en las horas y circunstancias que establecen los reglamentos " +
                    "de tránsito o no utilice las luces direccionales luminosas antes de efectuar un viraje o estacionamiento."),
            new CustomObject("15. La o el conductor que adelante a un vehículo de transporte escolar mientras este se encuentre estacionado, en lugares autorizados para tal efecto, y sus pasajeros estén " +
                    "embarcando o desembarcando."),
            new CustomObject("16. La o el conductor de vehículos de propiedad del sector público ecuatoriano que conduzca el vehículo oficial fuera de las horas de oficina, sin portar el respectivo salvoconducto."),
            new CustomObject("17. La o el conductor de vehículo de transporte público masivo que se niegue a transportar a los ciclistas con sus bicicletas, siempre que el vehículo cuente con las facilidades para " +
                    "transportarlas."),
            new CustomObject("18. La o el conductor que no respete el derecho preferente de los ciclistas en los desvíos, avenidas y carreteras, cruce de caminos, intersecciones no señalizadas y ciclovías."),
            new CustomObject("19. La o el conductor que invada con su vehículo, circulando o estacionándose, las vías asignadas para uso exclusivo de los ciclistas."),
            new CustomObject("20. La o el conductor de motocicletas, motonetas, bicimotos, tricar y cuadrones que transporte un número de personas superior a la capacidad permitida, de conformidad con lo establecido " +
                    "en los reglamentos de tránsito."),
            new CustomObject("21. La persona que altere la circulación y la seguridad peatonal por colocar obstáculos en la vía pública sin la respectiva autorización o sin fijar los avisos correspondientes."),
            new CustomObject("22. La o el conductor que deje en el interior del vehículo a niñas o niños solos, sin supervisión de una persona adulta."),
    };

    // Inciso 1 articulo 391 numeral
    private CustomObject[] A391N1 = new CustomObject[]{
            new CustomObject("1. La o el conductor de un vehículo automotor que circule contraviniendo las normas previstas en los reglamentos de tránsito y demás disposiciones aplicables, relacionadas con la " +
                    "emanación de gases."),
            new CustomObject("2. La persona que no conduzca su vehículo por la derecha en las vías de doble dirección."),
            new CustomObject("3. La o el conductor que invada con su vehículo las vías exclusivas asignadas a los buses de transporte rápido."),
            new CustomObject("4. La o el conductor de un vehículo automotor que no lleve en el mismo, un botiquín de primeros auxilios equipado y un extintor de incendios cargado y funcionando, de conformidad " +
                    "con lo establecido en los reglamentos de tránsito."),
            new CustomObject("5. La o el conductor que estacione un vehículo en los sitios prohibidos por la ley o los reglamentos de tránsito; o que, sin derecho, estacione su vehículo en los espacios destinados " +
                    "a un uso exclusivo de personas con discapacidad o mujeres embarazadas; o estacione su vehículo obstaculizando rampas de acceso para discapacitados, puertas de garaje o zonas de " +
                    "circulación peatonal. En caso que el conductor no se encuentre en el vehículo este será trasladado a uno de los sitios de retención vehicular."),
            new CustomObject("6. La persona que obstaculice el tránsito vehicular al quedarse sin combustible."),
            new CustomObject("7. La o el conductor de un vehículo automotor particular que transporte a niños sin las correspondientes seguridades, de conformidad con lo establecido en los reglamentos de tránsito."),
            new CustomObject("8. La o el conductor que no detenga el vehículo, antes de cruzar una línea férrea, de buses de transporte rápido en vías exclusivas o similares."),
            new CustomObject("9. La persona que conduzca o instale, sin autorización del organismo competente, en los vehículos particulares o públicos, sirenas o balizas de cualquier tipo, en cuyo caso además de " +
                    "la sanción prevista en el presente artículo, se le retirarán las balizas, o sirenas del vehículo."),
            new CustomObject("10. La o el conductor que en caso de desperfecto mecánico no use o no coloque adecuadamente los triángulos de seguridad, conforme lo establecido en los reglamentos de tránsito."),
            new CustomObject("11. La persona que conduzca un vehículo con vidrios con películas antisolares oscuras, polarizados o cualquier tipo de adhesivo que impidan la visibilidad del conductor, excepto los " +
                    "autorizados en el reglamento correspondiente o cuyo polarizado de origen sea de fábrica."),
            new CustomObject("12. La o el conductor que utilice el teléfono celular mientras conduce y no haga uso del dispositivo homologado de manos libres."),
            new CustomObject("13. La o el conductor de transporte público de servicio masivo que incumpla las tarifas preferenciales fijadas por la ley en beneficio de los niños, estudiantes, personas adultas " +
                    "mayores de sesenta y cinco años de edad y personas con capacidades especiales."),
            new CustomObject("14. La o el conductor que no encienda las luces del vehículo en horas de la noche o conduzca en sitios oscuros como túneles, con las luces apagadas."),
            new CustomObject("15. La o el conductor, controlador o ayudante de transporte público o comercial que maltrate de obra o de palabra a los usuarios."),
            new CustomObject("16. Las (sic) personas que, sin permiso de la autoridad de tránsito competente, realice actividades o competencias deportivas en las vías públicas, con vehículos de tracción humana o animal."),
            new CustomObject("17. La o el propietario de mecánicas, estaciones de servicio, talleres de bicicletas, motocicletas y de locales de reparación o adecuación de vehículos en general, que preste sus servicios " +
                    "en la vía pública."),
            new CustomObject("18. La o el propietario de vehículos de servicio público, comercial o privado que instale en sus vehículos equipos de vídeo o televisión en sitios que puedan provocar la distracción del conductor."),
            new CustomObject("19. La o el conductor de un vehículo que presta servicio de transporte urbano que circule con las puertas abiertas."),
            new CustomObject("20. La o el conductor de vehículos pesados que circule por zonas restringidas sin perjuicio de que se cumpla con lo estipulado en las ordenanzas municipales."),
            new CustomObject("21. La persona que conduzca un vehículo automotor sin portar su licencia de conducir."),
    };

    // Inciso 1 articulo 392 numeral
    private CustomObject[] A392N1 = new CustomObject[]{
            new CustomObject("1. La o el conductor que use inadecuada y reiteradamente la bocina u otros dispositivos sonoros contraviniendo las normas previstas en los reglamentos de tránsito y demás normas " +
                    "aplicables, referente a la emisión de ruidos."),
            new CustomObject("2. La o el conductor de transporte público de servicio masivo de personas y comercial cuyo vehículo circule sin los distintivos e identificación reglamentarios, sobre el tipo de " +
                    "servicio que presta la unidad que conduce."),
            new CustomObject("3. La persona con discapacidad que conduzca un vehículo adaptado a su discapacidad sin la identificación o distintivo correspondiente."),
            new CustomObject("4. La o el conductor de un vehículo de servicio público que no presente la lista de pasajeros, cuando se trate de transporte público interprovincial o internacional."),
            new CustomObject("5. La o el conductor que no mantenga la distancia prudente de seguimiento, de conformidad con los reglamentos de tránsito."),
            new CustomObject("6. La o el conductor que no utilice el cinturón de seguridad."),
            new CustomObject("7. La o el conductor de un vehículo de transporte público o comercial que no ponga a disposición de los pasajeros recipientes o fundas para recolección de basura o desechos."),
            new CustomObject("8. La o el peatón que en las vías públicas no transite por las aceras o sitios de seguridad destinados para el efecto."),
            new CustomObject("9. La o el peatón que, ante las señales de alarma o toque de sirena de un vehículo de emergencia, no deje la vía libre."),
            new CustomObject("10. La persona que desde el interior de un vehículo arroje a la vía pública desechos que contaminen el ambiente."),
            new CustomObject("11. La persona que ejerza actividad comercial o de servicio sobre las zonas de seguridad peatonal o calzadas."),
            new CustomObject("12. La o el ciclista o motociclista que circule por sitios en los que no le esté permitido."),
            new CustomObject("13. La o el comprador de un vehículo automotor que no registre, en el organismo de tránsito correspondiente, el traspaso de dominio del bien, dentro del plazo de treinta días, " +
                    "contado a partir de la fecha del respectivo contrato."),
            new CustomObject("14. La o el ciclista y conductor de vehículos de tracción animal que no respete la señalización reglamentaria respectiva."),
            new CustomObject("15. La o el propietario de un vehículo que instale, luces, faros o neblineros en sitios prohibidos del automotor, sin la respectiva autorización."),
    };

    private CustomObject[] blanco = new CustomObject[]{
            new CustomObject(""),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Actionbar
        Toolbar toolbar = findViewById(R.id.toolbararticulos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Edittext
        descripcion =(EditText) findViewById(R.id.Descipcion);
        //Spinner
        hintSpinnerarticulo = findViewById(R.id.Articulo);
        hintSpinnerinciso = findViewById(R.id.inciso);
        hintSpinnerinciso.setEnabled(false);
        hintSpinnernumeral = findViewById(R.id.Numeral);
        hintSpinnernumeral.setEnabled(false);
        //Textview
        articulo1 = (TextView) findViewById(R.id.Articuloi);
        inciso1 = (TextView) findViewById(R.id.Incisoi);
        numeral1 = (TextView) findViewById(R.id.Numerali);
        articulo();
        mostrarestado();
    }

    static class CustomObject {
        private String name;

        CustomObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public void articulo(){
        //Muestra los articulos
        final HintSpinnerAdapter hintSpinnerAdapter = new HintSpinnerAdapter<CustomObject>(this, COIPA, "Elija un artículo") {
            @Override
            public String getLabelFor(CustomObject object) {
                return object.getName();
            }
        };
        hintSpinnerarticulo.setAdapter(hintSpinnerAdapter);
        inciso();
    }
    //Muestra incisos deacuerdo al artículo seleecionado
    public void inciso (){
        hintSpinnerinciso.setEnabled(true);
        hintSpinnerarticulo.setOnItemSelectedListener(

                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                        int seleccion = posicion;
                        switch(seleccion) {
                            case 0:
                                final HintSpinnerAdapter hintSpinnerAdapter383 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A383, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter383);
                                //Muestra el articulo seleccionado
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco0 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco0);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(

                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        final HintSpinnerAdapter hintSpinnerAdapter383n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter383n1);
                                                        break;
                                                    case 1:
                                                        final HintSpinnerAdapter hintSpinnerAdapter383n2 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter383n2);
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A383[posicion].getName());
                                                numeral1.setText("");
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        }
                                );
                                break;
                            case 1:
                                final HintSpinnerAdapter hintSpinnerAdapter384 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A384, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter384);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco1);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(

                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        final HintSpinnerAdapter hintSpinnerAdapter384n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter384n1);
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A384[posicion].getName());
                                                numeral1.setText("");
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        }
                                );
                                break;
                            case 2:
                                final HintSpinnerAdapter hintSpinnerAdapter385 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A385, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter385);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco2 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco2);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(
                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter385n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A385N1, "Elija numeral") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter385n1);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A385N1[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    case 1:
                                                        hintSpinnernumeral.setEnabled(false);
                                                        final HintSpinnerAdapter hintSpinnerAdapter385n2 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter385n2);
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A385[posicion].getName());
                                                numeral1.setText("");
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        }
                                );
                                break;
                            case 3:
                                final HintSpinnerAdapter hintSpinnerAdapter386 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A386, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter386);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco3 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco3);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(

                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter386n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A386N1, "Elija numeral") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter386n1);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A386N1[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    case 1:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter386n2 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A386N2, "") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter386n2);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A386N2[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A386[posicion].getName());
                                                numeral1.setText("");
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                            }
                                        }
                                );
                                break;
                            case 4:
                                final HintSpinnerAdapter hintSpinnerAdapter387 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A387, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter387);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco4 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco4);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(
                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter387n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A387N1, "Elija numeral") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter387n1);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A387N1[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A387[posicion].getName());
                                                numeral1.setText("");
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                            }
                                        }
                                );
                                break;
                            case 5:
                                final HintSpinnerAdapter hintSpinnerAdapter388 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A388, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter388);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco5 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco5);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(

                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter388n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A388N1, "Elija numeral") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter388n1);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A388N1[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A388[posicion].getName());
                                                numeral1.setText("");
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        }
                                );
                                break;
                            case 6:
                                final HintSpinnerAdapter hintSpinnerAdapter389 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A389, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter389);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco6 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco6);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(

                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter389n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A389N1, "Elija numeral") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter389n1);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A389N1[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A389[posicion].getName());
                                                numeral1.setText("");
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        }
                                );
                                break;
                            case 7:
                                final HintSpinnerAdapter hintSpinnerAdapter390 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A390, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter390);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco7 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco7);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(
                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter390n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A390N1, "Elija numeral") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter390n1);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A390N1[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A390[posicion].getName());
                                                numeral1.setText("");
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        }
                                );
                                break;
                            case 8:
                                final HintSpinnerAdapter hintSpinnerAdapter391 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A391, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter391);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco8 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco8);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(

                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter391n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A391N1, "Elija numeral") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter391n1);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A391N1[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A391[posicion].getName());
                                                numeral1.setText("");
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        }
                                );
                                break;
                            case 9:
                                final HintSpinnerAdapter hintSpinnerAdapter392 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A392, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapter392);
                                hintSpinnernumeral.setEnabled(false);
                                final HintSpinnerAdapter hintSpinnerAdapterblanco9 = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnernumeral.setAdapter(hintSpinnerAdapterblanco9);
                                //Establece numerales en caso que haya
                                hintSpinnerinciso.setOnItemSelectedListener(

                                        new AdapterView.OnItemSelectedListener() {
                                            public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                int seleccion = posicion;
                                                switch (seleccion) {
                                                    case 0:
                                                        hintSpinnernumeral.setEnabled(true);
                                                        final HintSpinnerAdapter hintSpinnerAdapter392n1 = new HintSpinnerAdapter<CustomObject>(Articulos.this, A392N1, "Elija numeral") {
                                                            @Override
                                                            public String getLabelFor(CustomObject object) {
                                                                return object.getName();
                                                            }
                                                        };
                                                        hintSpinnernumeral.setAdapter(hintSpinnerAdapter392n1);
                                                        //Establece el valor del numeral escogido en el edittext
                                                        hintSpinnernumeral.setOnItemSelectedListener(
                                                                new AdapterView.OnItemSelectedListener() {
                                                                    public void onItemSelected(AdapterView<?> spn, android.view.View v, int posicion, long id) {
                                                                        numeral1.setText(A392N1[posicion].getName());
                                                                    }
                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                                                    }
                                                                }
                                                        );
                                                        break;
                                                    default:
                                                }
                                                inciso1.setText(A391[posicion].getName());
                                                numeral1.setText("");
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                            }
                                        }
                                );
                                break;
                            default:
                                final HintSpinnerAdapter hintSpinnerAdapterblanco = new HintSpinnerAdapter<CustomObject>(Articulos.this, blanco, "Elija inciso") {
                                    @Override
                                    public String getLabelFor(CustomObject object) {
                                        return object.getName();
                                    }
                                };
                                hintSpinnerinciso.setAdapter(hintSpinnerAdapterblanco);
                        }

                        articulo1.setText(COIPA[posicion].getName());
                        inciso1.setText("");
                        numeral1.setText("");

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (validar ())
            onBackPressed();
        return true;
    }

    //presentar estado activity
    public void mostrarestado (){
        SharedPreferences prefe = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        descripcion.setText(prefe.getString("descipcion", ""));
        articulo1.setText(prefe.getString("articulo", ""));
        inciso1.setText(prefe.getString("inciso", ""));
        numeral1.setText(prefe.getString("numeral", ""));
    }

    //guarda estado activity
    public void guardaestado() {
        SharedPreferences preferencias=getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("descipcion", descripcion.getText().toString());
        editor.putString("articulo", articulo1.getText().toString());
        editor.putString("inciso", inciso1.getText().toString());
        editor.putString("numeral", numeral1.getText().toString());
        editor.apply();
        finish();
    }

//    public void guardaDB() {
//        AdminSQLiteOpenHelper helper = new AdminSQLiteOpenHelper (this, Constantes.DB, null, 1);
//        String descipcion = descripcion.getText().toString();
//        String articulo = articulo1.getText().toString();
//        String inciso = inciso1.getText().toString();
//        String numeral = numeral1.getText().toString();
//        Articulos_Coip art = new Articulos_Coip(articulo, numeral, inciso, descipcion);
//        Constantes.articulo = art;
//        helper.crearAritculo (art);
//    }

    //controla boton atras
    @Override
    public void onBackPressed()
    {
        guardaestado();
    }
}
