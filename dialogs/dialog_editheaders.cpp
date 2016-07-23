#include "dialog_editheaders.h"
#include <QFormLayout>
#include <QDialogButtonBox>
#include <QGroupBox>
#include <QDebug>


DialogEditHeaders::DialogEditHeaders(QMap<QString, QString> *headers, QWidget *parent) :
    QDialog(parent)
{

    leEvent = new QLineEdit();
    leSite = new QLineEdit();
    leDate = new QLineEdit();
    leRound = new QLineEdit();
    leWhite = new QLineEdit();
    leBlack = new QLineEdit();
    leECO = new QLineEdit();
    rbWhiteWins = new QRadioButton("1-0");
    rbBlackWins = new QRadioButton("0-1");
    rbDraw = new QRadioButton("1/2 - 1/2");
    rbUndefined = new QRadioButton("*");

    lblSite = new QLabel("Site");
    lblDate = new QLabel("Date");
    lblRound = new QLabel("Round");
    lblEvent = new QLabel("Event");
    lblWhite = new QLabel("White");
    lblBlack = new QLabel("Black");
    lblResult = new QLabel("Result");
    lblEco = new QLabel("Eco");

    QDialogButtonBox *buttonBox = new QDialogButtonBox(QDialogButtonBox::Ok| QDialogButtonBox::Cancel);

    connect(buttonBox, &QDialogButtonBox::accepted, this, &DialogEditHeaders::accept);
    connect(buttonBox, &QDialogButtonBox::rejected, this, &DialogEditHeaders::reject);

    QFormLayout *formLayout = new QFormLayout;
    formLayout->addRow(lblSite, leSite);
    formLayout->addRow(lblDate, leDate);
    formLayout->addRow(lblRound, leRound);
    formLayout->addRow(lblEvent, leEvent);
    formLayout->addRow(lblWhite, leWhite);
    formLayout->addRow(lblBlack, leBlack);
    formLayout->addRow(lblEco, leECO);

    QGroupBox *grbData = new QGroupBox(tr("Game Data"));
    QGroupBox *grbRes = new QGroupBox(tr("Result"));

    grbData->setLayout(formLayout);
    QHBoxLayout *hbox = new QHBoxLayout();
    hbox->addWidget(rbWhiteWins);
    hbox->addWidget(rbBlackWins);
    hbox->addWidget(rbDraw);
    hbox->addWidget(rbUndefined);
    grbRes->setLayout(hbox);

    QVBoxLayout *layout = new QVBoxLayout();
    layout->addWidget(grbData);
    layout->addWidget(grbRes);
    layout->addWidget(buttonBox);

    leDate->setText(headers->value("Date"));
    leSite->setText(headers->value("Site"));
    leRound->setText(headers->value("Round"));
    leEvent->setText(headers->value("Event"));
    leWhite->setText(headers->value("White"));
    leBlack->setText(headers->value("Black"));
    leECO->setText(headers->value("ECO"));

    rbWhiteWins->setChecked(true);
    QString res = headers->value("Result");

    if(!res.isEmpty()) {
        if(res == QString("0-1")) {
            rbBlackWins->setChecked(true);
        }
        if(res == QString("1/2-1/2")) {
            rbDraw->setChecked(true);
        }
        if(res == QString("*")) {
            rbUndefined->setChecked(true);
        }
    }
    setLayout(layout);

}